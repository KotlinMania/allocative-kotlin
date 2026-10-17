// port-lint: source impls/either.rs
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
import io.github.kotlinmania.either.Either

private val KEY_LEFT: Key = Key.new("Left")
private val KEY_RIGHT: Key = Key.new("Right")

public fun <A : Allocative, B : Allocative> Either<A, B>.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
    leftSizeBytes: Int = 8,
    rightSizeBytes: Int = 8,
) {
    val self = visitor.enter(Key.new("either::Either"), selfSizeBytes)
    when (this) {
        is Either.Left -> self.visitField(KEY_LEFT, leftSizeBytes, value)
        is Either.Right -> self.visitField(KEY_RIGHT, rightSizeBytes, value)
    }
    self.exit()
}
