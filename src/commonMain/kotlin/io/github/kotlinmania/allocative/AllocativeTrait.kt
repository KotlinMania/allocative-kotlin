// port-lint: source allocative_trait.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

/**
 * This trait allows traversal of object graph.
 *
 * # Proc macro
 *
 * Typically implemented with proc macro. Like this:
 *
 * ```
 * import io.github.kotlinmania.allocative.Allocative
 *
 * @Allocative
 * class Foo(val x: UInt, val y: String)
 * ```
 *
 * Proc macro supports two attributes: `@allocative(skip)` and
 * `@allocative(bound = "...")`.
 *
 * ## `@allocative(skip)`
 *
 * `@allocative(skip)` can be used to skip field from traversal (for example,
 * to skip fields which are not `Allocative`, and can be skipped because they
 * are cheap).
 *
 * ```
 * import io.github.kotlinmania.allocative.Allocative
 *
 * /** This does not implement `Allocative`. */
 * class Unsupported
 *
 * @Allocative
 * class Bar(
 *     @allocative(skip) val unsupported: Unsupported,
 * )
 * ```
 *
 * ## `@allocative(bound = "...")`
 *
 * `@allocative(bound = "...")` can be used to overwrite the bounds that are
 * added to the generics of the implementation.
 *
 * An empty string (`@allocative(bound = "")`) simply erases all bounds. It
 * adds all type variables found in the type to the list of generics but with
 * an empty bound. As an example
 *
 * ```
 * import io.github.kotlinmania.allocative.Allocative
 *
 * class Unsupported
 *
 * @Allocative
 * @allocative(bound = "")
 * class Baz<T>
 * ```
 *
 * Would generate an instance
 *
 * ```
 * class Baz<T> : Allocative { ... }
 * ```
 *
 * Alternatively you can use the string to provide custom bounds. The string in
 * this case is used *verbatim* as the bounds, which affords great flexibility,
 * but also necessitates that all type variables must be mentioned or will be
 * unbound (compile error). As an example we may derive a size of a `Map`
 * by ignoring the hasher type.
 *
 * ```
 * @allocative(bound = "K: Allocative, V: Allocative, S")
 * class HashMap<K, V, S>
 * ```
 *
 * Which generates
 *
 * ```
 * class HashMap<K : Allocative, V : Allocative, S> : Allocative { ... }
 * ```
 *
 * ## `@allocative(visit = ...)`
 *
 * This annotation is used to provide a custom visit method for a given field. This
 * is especially useful if the type of the field does not implement `Allocative`.
 *
 * The annotation takes the path to a method with a signature `(T, Visitor) -> Unit`
 * where `T` is the type of the field. The function you provide is basically the same
 * as if you implemented [visit].
 *
 * As an example
 *
 * ```
 * import io.github.kotlinmania.allocative.Allocative
 * import io.github.kotlinmania.allocative.Visitor
 * import io.github.kotlinmania.allocative.key.Key
 *
 * class Unsupported<T>(private val elems: List<T>) {
 *     fun iterElems(): List<T> = elems
 * }
 *
 * @Allocative
 * class Bar(
 *     @allocative(visit = ::visitUnsupported)
 *     val unsupported: Unsupported<UInt>,
 * )
 *
 * fun visitUnsupported(u: Unsupported<UInt>, visitor: Visitor) {
 *     val elemKey = Key.new("elements")
 *     val v = visitor.enterSelf(u, sizeBytes = 0)
 *     for (element in u.iterElems()) {
 *         v.visitField(elemKey, sizeBytes = UInt.SIZE_BYTES, element)
 *     }
 *     v.exit()
 * }
 * ```
 */
public interface Allocative {
    public fun visit(visitor: Visitor)
}
