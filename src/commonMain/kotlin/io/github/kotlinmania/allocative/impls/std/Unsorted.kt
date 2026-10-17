// port-lint: source impls/std/unsorted.rs
package io.github.kotlinmania.allocative.impls.std

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.Allocative
import io.github.kotlinmania.allocative.Visitor
import io.github.kotlinmania.allocative.impls.DATA_NAME
import io.github.kotlinmania.allocative.impls.PTR_NAME
import io.github.kotlinmania.allocative.impls.UNUSED_CAPACITY_NAME
import io.github.kotlinmania.allocative.key.Key

private val KEY_STR: Key = Key.new("str")
private val KEY_SOME: Key = Key.new("Some")

public fun CharSequence.visit(visitor: Visitor) {
    visitor.visitSimple(KEY_STR, length)
}

public fun String.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 24,
    pointerSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    val bytesVisitor = v.enterUnique(PTR_NAME, pointerSizeBytes)
    val byteLen = encodeToByteArray().size
    bytesVisitor.visitSimple(KEY_STR, byteLen)
    bytesVisitor.exit()
    v.exit()
}

public fun <T : Allocative> T?.visitOption(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
    valueSizeBytes: Int = 8,
) {
    val v = visitor.enter(Key.new("Option"), selfSizeBytes)
    if (this != null) {
        v.visitField(KEY_SOME, valueSizeBytes, this)
    }
    v.exit()
}

public fun <T : Allocative> List<T>.visit(
    visitor: Visitor,
    capacity: Int = size,
    selfSizeBytes: Int = 24,
    pointerSizeBytes: Int = 8,
    elementSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    if (capacity != 0 && elementSizeBytes != 0) {
        val uniqueVisitor = v.enterUnique(PTR_NAME, pointerSizeBytes)
        uniqueVisitor.visitSlice(this)
        val unused = (capacity - size).coerceAtLeast(0)
        uniqueVisitor.visitSimple(UNUSED_CAPACITY_NAME, unused * elementSizeBytes)
        uniqueVisitor.exit()
    }
    v.exit()
}

public fun <T : Allocative> ArrayDeque<T>.visit(
    visitor: Visitor,
    capacity: Int = size,
    selfSizeBytes: Int = 24,
    pointerSizeBytes: Int = 8,
    elementSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    if (capacity != 0 && elementSizeBytes != 0) {
        val uniqueVisitor = v.enterUnique(PTR_NAME, pointerSizeBytes)
        for (item in this) {
            uniqueVisitor.visitField(DATA_NAME, elementSizeBytes, item)
        }
        val unused = (capacity - size).coerceAtLeast(0)
        uniqueVisitor.visitSimple(UNUSED_CAPACITY_NAME, unused * elementSizeBytes)
        uniqueVisitor.exit()
    }
    v.exit()
}
