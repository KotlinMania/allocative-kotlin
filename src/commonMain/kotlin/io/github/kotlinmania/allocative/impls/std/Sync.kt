// port-lint: source allocative/src/impls/std/sync.rs
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
import io.github.kotlinmania.allocative.impls.PTR_NAME
import io.github.kotlinmania.allocative.key.Key

public class AllocativeAtomicInt(
    public var value: Int,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeAtomicInt::class, Int.SIZE_BYTES)
    }
}

public class AllocativeAtomicLong(
    public var value: Long,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeAtomicLong::class, Long.SIZE_BYTES)
    }
}

public class AllocativeAtomicBoolean(
    public var value: Boolean,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeAtomicBoolean::class, 1)
    }
}

public class AllocativeShared<T : Allocative>(
    public val value: T,
    public val valueSizeBytes: Int = 8,
) : Allocative {
    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, 8)
        val sharedVisitor = self.enterShared(PTR_NAME, 8, value)
        if (sharedVisitor != null) {
            val innerVisitor = sharedVisitor.enter(KEY_INNER, valueSizeBytes)
            value.visit(innerVisitor)
            innerVisitor.exit()
            sharedVisitor.exit()
        }
        self.exit()
    }

    private companion object {
        val KEY_INNER: Key = Key.new("SharedInner")
    }
}
