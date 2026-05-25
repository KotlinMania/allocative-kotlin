// port-lint: source impls/indexmap.rs
package io.github.kotlinmania.allocative.impls

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
import io.github.kotlinmania.allocative.key.Key
import io.github.kotlinmania.indexmap.IndexMap
import io.github.kotlinmania.indexmap.IndexSet

// Add approximate allocations for hashbrown RawTable.
internal fun addRawTableForLen(visitor: Visitor, len: Int, elementSizeBytes: Int) {
    if (len != 0) {
        val sizeOfRawTable = Int.SIZE_BYTES
        val nested = visitor.enterUnique(Key.new("raw_table"), sizeOfRawTable)
        nested.visitSimple(Key.new("alloc"), rawTableAllocSizeForLen(len, elementSizeBytes))
        nested.exit()
    }
}

// Visit an IndexSet with the same shape as the upstream Allocative impl.
//
// Kotlin cannot retroactively implement Allocative for an external class, so
// callers use this helper from generated or handwritten Allocative visitors.
public fun <T : Allocative> visitIndexSet(
    set: IndexSet<T>,
    visitor: Visitor,
    selfSizeBytes: Int,
    referenceSizeBytes: Int,
    elementSizeBytes: Int,
) {
    val selfVisitor = visitor.enterSelf(set, selfSizeBytes)
    val dataVisitor = selfVisitor.enterUnique(Key.new("data"), referenceSizeBytes)
    for (value in set) {
        dataVisitor.visitField(Key.new("value"), elementSizeBytes, value)
    }
    val unusedCapacity = set.capacity() - set.len()
    dataVisitor.visitSimple(UNUSED_CAPACITY_NAME, unusedCapacity * elementSizeBytes)
    dataVisitor.exit()
    addRawTableForLen(selfVisitor, set.len(), Int.SIZE_BYTES)
    selfVisitor.exit()
}

// Visit an IndexMap with the same shape as the upstream Allocative impl.
//
// Kotlin cannot retroactively implement Allocative for an external class, so
// callers use this helper from generated or handwritten Allocative visitors.
public fun <K : Allocative, V : Allocative> visitIndexMap(
    map: IndexMap<K, V>,
    visitor: Visitor,
    selfSizeBytes: Int,
    referenceSizeBytes: Int,
    keySizeBytes: Int,
    valueSizeBytes: Int,
) {
    val selfVisitor = visitor.enterSelf(map, selfSizeBytes)
    val dataVisitor = selfVisitor.enterUnique(Key.new("data"), referenceSizeBytes)
    for ((key, value) in map) {
        dataVisitor.visitField(Key.new("key"), keySizeBytes, key)
        dataVisitor.visitField(Key.new("value"), valueSizeBytes, value)
    }
    val unusedCapacity = map.capacity() - map.len()
    dataVisitor.visitSimple(UNUSED_CAPACITY_NAME, unusedCapacity * (keySizeBytes + valueSizeBytes))
    dataVisitor.exit()
    addRawTableForLen(selfVisitor, map.len(), Int.SIZE_BYTES)
    selfVisitor.exit()
}
