// port-lint: source allocative/src/impls/either.rs
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

public sealed class Either<out A : Allocative, out B : Allocative> : Allocative {
    public class Left<A : Allocative>(
        public val value: A,
        public val sizeBytes: Int = 8,
    ) : Either<A, Nothing>() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, sizeBytes)
            self.visitField(KEY_LEFT, sizeBytes, value)
            self.exit()
        }
    }

    public class Right<B : Allocative>(
        public val value: B,
        public val sizeBytes: Int = 8,
    ) : Either<Nothing, B>() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, sizeBytes)
            self.visitField(KEY_RIGHT, sizeBytes, value)
            self.exit()
        }
    }

    private companion object {
        val KEY_LEFT: Key = Key.new("Left")
        val KEY_RIGHT: Key = Key.new("Right")
    }
}
