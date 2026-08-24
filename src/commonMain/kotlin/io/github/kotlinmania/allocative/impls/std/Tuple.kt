// port-lint: source impls/std/tuple.rs
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

public object AllocativeUnit : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeUnit::class, 0)
    }
}

public class AllocativePair<A : Allocative, B : Allocative>(
    public val first: A,
    public val second: B,
    public val firstSizeBytes: Int = 8,
    public val secondSizeBytes: Int = 8,
) : Allocative {
    override fun visit(visitor: Visitor) {
        val nested = visitor.enterSelf(this, firstSizeBytes + secondSizeBytes)
        nested.visitField(KEY_0, firstSizeBytes, first)
        nested.visitField(KEY_1, secondSizeBytes, second)
        nested.exit()
    }

    private companion object {
        val KEY_0: Key = Key.new("0")
        val KEY_1: Key = Key.new("1")
    }
}

public class AllocativeTriple<A : Allocative, B : Allocative, C : Allocative>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val firstSizeBytes: Int = 8,
    public val secondSizeBytes: Int = 8,
    public val thirdSizeBytes: Int = 8,
) : Allocative {
    override fun visit(visitor: Visitor) {
        val nested = visitor.enterSelf(this, firstSizeBytes + secondSizeBytes + thirdSizeBytes)
        nested.visitField(KEY_0, firstSizeBytes, first)
        nested.visitField(KEY_1, secondSizeBytes, second)
        nested.visitField(KEY_2, thirdSizeBytes, third)
        nested.exit()
    }

    private companion object {
        val KEY_0: Key = Key.new("0")
        val KEY_1: Key = Key.new("1")
        val KEY_2: Key = Key.new("2")
    }
}
