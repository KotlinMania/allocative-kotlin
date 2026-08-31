// port-lint: source allocative/src/impls/std/time.rs
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
import kotlin.time.Duration
import kotlin.time.Instant

public class AllocativeInstant(
    private val value: Instant,
    private val sizeBytes: Int = 16,
) : Allocative {
    public fun toEpochMilliseconds(): Long = value.toEpochMilliseconds()

    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeInstant::class, sizeBytes)
    }
}

public class AllocativeDuration(
    private val value: Duration,
    private val sizeBytes: Int = 16,
) : Allocative {
    public fun inWholeNanoseconds(): Long = value.inWholeNanoseconds

    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeDuration::class, sizeBytes)
    }
}
