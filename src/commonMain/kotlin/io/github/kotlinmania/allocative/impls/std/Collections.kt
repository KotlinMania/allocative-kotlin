// port-lint: source allocative/src/impls/std/collections.rs
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

public class AllocativeMap<K : Allocative, V : Allocative>(
    private val map: Map<K, V>,
    private val referenceSizeBytes: Int = 8,
    private val keySizeBytes: Int = 8,
    private val valueSizeBytes: Int = 8,
) : Allocative {
    public fun size(): Int = map.size

    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, referenceSizeBytes)
        self.visitGenericMapFields(
            map.entries.map { it.key to it.value },
            referenceSizeBytes,
            keySizeBytes,
            valueSizeBytes,
        )
        self.exit()
    }
}

public class AllocativeSet<K : Allocative>(
    private val set: Set<K>,
    private val referenceSizeBytes: Int = 8,
    private val keySizeBytes: Int = 8,
) : Allocative {
    public fun size(): Int = set.size

    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, referenceSizeBytes)
        self.visitGenericSetFields(set.toList(), referenceSizeBytes, keySizeBytes)
        self.exit()
    }
}

public class AllocativeList<T : Allocative>(
    private val list: List<T>,
    public val capacity: Int = list.size,
    public val elementSizeBytes: Int = 8,
) : Allocative {
    public fun size(): Int = list.size

    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, elementSizeBytes * list.size)
        self.visitVecLikeBody(list, capacity, elementSizeBytes)
        self.exit()
    }
}
