// port-lint: tests impls/either.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.impls.Either
import io.github.kotlinmania.allocative.impls.std.AllocativeInt
import kotlin.test.Test
import kotlin.test.assertTrue

class EitherTest {
    @Test
    fun testEitherLeft() {
        val either: Either<AllocativeInt, AllocativeInt> = Either.Left(AllocativeInt(1))
        val fg = FlameGraphBuilder()
        fg.visitRoot(either)
        val output = fg.finishAndWriteFlameGraph()
        assertTrue(output.contains("Left"), "Output: $output")
    }
}
