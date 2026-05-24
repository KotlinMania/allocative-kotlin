// port-lint: source impls/hashbrown_util.rs
package io.github.kotlinmania.allocative.impls

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

/** Approximate allocated memory for hashbrown `RawTable`. */
internal fun rawTableAllocSizeForLen(len: Int, elementSizeBytes: Int): Int {
    require(len >= 0) { "len must be non-negative" }
    require(elementSizeBytes >= 0) { "elementSizeBytes must be non-negative" }

    val buckets =
        when {
            len == 0 -> 0
            len < 4 -> 4
            else -> nextPowerOfTwo(len)
        }
    val sizeOfControlByte = 1
    return (elementSizeBytes + sizeOfControlByte) * buckets
}

private fun nextPowerOfTwo(value: Int): Int {
    var buckets = 1
    while (buckets < value) {
        buckets = buckets shl 1
    }
    return buckets
}
