// port-lint: source impls/std/mem.rs
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
import io.github.kotlinmania.allocative.key.Key

internal class ManuallyDrop<T : Allocative>(
    private val inner: T,
    private val selfSizeBytes: Int,
    private val innerSizeBytes: Int,
) : Allocative {
    override fun visit(visitor: Visitor) {
        val nested = visitor.enterSelf(this, selfSizeBytes)
        nested.visitField(INNER_NAME, innerSizeBytes, inner)
        nested.exit()
    }

    private companion object {
        val INNER_NAME: Key = Key.new("inner")
    }
}
