// port-lint: source impls/std/cell.rs
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
import io.github.kotlinmania.allocative.impls.DATA_NAME

public class AllocativeRefCell<T : Allocative>(
    private var value: T,
    private val valueSizeBytes: Int = 8,
) : Allocative {
    public fun get(): T = value

    public fun set(newValue: T) {
        value = newValue
    }

    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, valueSizeBytes)
        self.visitField(DATA_NAME, valueSizeBytes, value)
        self.exit()
    }
}

public class AllocativeOnceCell<T : Allocative>(
    private val valueSizeBytes: Int = 8,
) : Allocative {
    private var value: T? = null

    public fun get(): T? = value

    public fun set(v: T): Boolean {
        if (value == null) {
            value = v
            return true
        }
        return false
    }

    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, valueSizeBytes)
        val v = value
        if (v != null) {
            self.visitField(DATA_NAME, valueSizeBytes, v)
        }
        self.exit()
    }
}
