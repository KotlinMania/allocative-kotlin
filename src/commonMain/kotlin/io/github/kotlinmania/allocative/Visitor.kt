// port-lint: source visitor.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.impls.CAPACITY_NAME
import io.github.kotlinmania.allocative.impls.DATA_NAME
import io.github.kotlinmania.allocative.impls.KEY_NAME
import io.github.kotlinmania.allocative.impls.UNUSED_CAPACITY_NAME
import io.github.kotlinmania.allocative.impls.VALUE_NAME
import io.github.kotlinmania.allocative.key.Key
import kotlin.reflect.KClass

/**
 * Actual implementation of the visitor.
 *
 * At the moment there's only one implementation, the one which generates flame graph,
 * and this trait is crate-private. This may change in the future.
 */
internal interface VisitorImpl {
    /**
     * Enter simple field like `UInt`.
     * All sizes are in bytes.
     */
    fun enterInlineImpl(name: Key, size: Int, parent: NodeKind)

    /** Enter a field which points to heap-allocated unique memory. */
    fun enterUniqueImpl(name: Key, size: Int, parent: NodeKind)

    /**
     * Enter a field which points to heap-allocated shared memory.
     * This function returns `false` if the referenced object was already visited.
     */
    fun enterSharedImpl(name: Key, size: Int, sharedIdentity: Any, parent: NodeKind): Boolean

    /**
     * Exit the field. Each `enter` must be matched by `exit`.
     * `Visitor` wrapper guarantees that.
     */
    fun exitInlineImpl()

    fun exitUniqueImpl()

    fun exitSharedImpl()

    /** Exit "root" visitor. */
    fun exitRootImpl()
}

internal enum class NodeKind {
    Inline,
    Unique,
    Shared,
    Root,
}

/**
 * Must call [exit].
 *
 * Size-bearing methods take an explicit `sizeBytes` parameter because Kotlin
 * does not expose an automatic object-size intrinsic. The caller is responsible
 * for providing that size.
 */
public class Visitor internal constructor(
    internal val visitor: VisitorImpl,
    internal val nodeKind: NodeKind,
) {

    public fun enter(name: Key, size: Int): Visitor {
        visitor.enterInlineImpl(name, size, nodeKind)
        return Visitor(visitor, NodeKind.Inline)
    }

    public fun enterUnique(name: Key, size: Int): Visitor {
        visitor.enterUniqueImpl(name, size, nodeKind)
        return Visitor(visitor, NodeKind.Unique)
    }

    /**
     * Enter a field containing a shared reference.
     *
     * This functions does nothing and returns `null`
     * if the referenced object was previously visited.
     */
    public fun enterShared(name: Key, size: Int, sharedIdentity: Any): Visitor? {
        return if (visitor.enterSharedImpl(name, size, sharedIdentity, nodeKind)) {
            Visitor(visitor, NodeKind.Shared)
        } else {
            null
        }
    }

    /**
     * This function is typically called as the first function of an `Allocative`
     * implementation to record self.
     *
     * Kotlin has no automatic type-size intrinsic, so [sizeBytes] is explicit.
     */
    public fun enterSelfSized(type: KClass<*>, sizeBytes: Int): Visitor {
        return enter(Key.forTypeName(type), sizeBytes)
    }

    /**
     * This function is typically called as first function of an `Allocative`
     * implementation to record self.
     *
     * Kotlin has no automatic value-size intrinsic, so [sizeBytes] is explicit.
     */
    public fun enterSelf(self: Any, sizeBytes: Int): Visitor {
        return enter(Key.forTypeName(self::class), sizeBytes)
    }

    /** Visit simple sized field (e.g. `UInt`) without descending into children. */
    public fun visitSimple(name: Key, size: Int) {
        enter(name, size).exit()
    }

    /** Visit simple sized field (e.g. `UInt`) without descending into children. */
    public fun visitSimpleSized(type: KClass<*>, sizeBytes: Int) {
        enterSelfSized(type, sizeBytes).exit()
    }

    /**
     * Visit a field by delegating to its [Allocative.visit].
     *
     * Kotlin has no automatic value-size intrinsic, so [sizeBytes] is explicit.
     */
    public fun visitField(name: Key, sizeBytes: Int, field: Allocative) {
        visitFieldWith(name, sizeBytes) { visitor ->
            field.visit(visitor)
        }
    }

    /**
     * Similar to [visitField] but instead of calling [Allocative.visit] for
     * whichever is the field type, you can provide a custom closure to call
     * instead.
     *
     * Useful if the field type does not implement [Allocative].
     */
    public fun visitFieldWith(name: Key, fieldSize: Int, visit: (Visitor) -> Unit) {
        val v = enter(name, fieldSize)
        visit(v)
        v.exit()
    }

    /**
     * Iterate the list, calling [Allocative.visit] on each element.
     *
     * Kotlin has no ownership-drop or automatic type-size intrinsic, so the
     * upstream no-descend shortcut for reference-free elements is not reproduced;
     * this always iterates.
     */
    public fun <T : Allocative> visitSlice(elements: List<T>) {
        visitIter(elements)
    }

    /**
     * Iterate, calling [Allocative.visit] on each element.
     *
     * Kotlin has no ownership-drop or automatic type-size intrinsic, so the
     * upstream no-descend shortcut is not reproduced; this always
     * iterates.
     */
    public fun <T : Allocative> visitIter(iter: Iterable<T>) {
        for (item in iter) {
            item.visit(this)
        }
    }

    /**
     * Visit the body of a list-like container.
     *
     * Kotlin has no automatic type-size intrinsic, so the per-element size is taken
     * as [elementSizeBytes]. The caller passes `data` (the populated list)
     * and `capacity` (the total allocated capacity).
     */
    public fun <T : Allocative> visitVecLikeBody(data: List<T>, capacity: Int, elementSizeBytes: Int) {
        visitFieldWith(CAPACITY_NAME, elementSizeBytes * capacity) { visitor ->
            visitor.visitSlice(data)
            val unused = (capacity - data.size).coerceAtLeast(0)
            visitor.visitSimple(UNUSED_CAPACITY_NAME, elementSizeBytes * unused)
        }
    }

    public fun <K : Allocative, V : Allocative> visitGenericMapFields(
        entries: Iterable<Pair<K, V>>,
        referenceSizeBytes: Int,
        keySizeBytes: Int,
        valueSizeBytes: Int,
    ) {
        visitFieldWith(DATA_NAME, referenceSizeBytes) { visitor ->
            for ((k, v) in entries) {
                visitor.visitField(KEY_NAME, keySizeBytes, k)
                visitor.visitField(VALUE_NAME, valueSizeBytes, v)
            }
        }
    }

    public fun <K : Allocative> visitGenericSetFields(
        entries: Iterable<K>,
        referenceSizeBytes: Int,
        keySizeBytes: Int,
    ) {
        visitFieldWith(DATA_NAME, referenceSizeBytes) { visitor ->
            for (k in entries) {
                visitor.visitField(KEY_NAME, keySizeBytes, k)
            }
        }
    }

    private fun exitImpl() {
        when (nodeKind) {
            NodeKind.Inline -> visitor.exitInlineImpl()
            NodeKind.Unique -> visitor.exitUniqueImpl()
            NodeKind.Shared -> visitor.exitSharedImpl()
            NodeKind.Root -> visitor.exitRootImpl()
        }
    }

    public fun exit() {
        exitImpl()
        // Kotlin has no destructor for this wrapper; callers must explicitly
        // close each [Visitor] exactly once.
    }
}
