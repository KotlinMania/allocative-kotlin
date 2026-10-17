// port-lint: source impls/once_cell.rs
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
import io.github.kotlinmania.oncecell.sync.Lazy as SyncLazy
import io.github.kotlinmania.oncecell.sync.OnceCell as SyncOnceCell

public fun <T : Allocative> SyncOnceCell<T>.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    val value = get()
    if (value != null) {
        value.visit(v)
    }
    v.exit()
}

public fun <T : Allocative> SyncLazy<T>.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    val value = SyncLazy.get(this)
    if (value != null) {
        value.visit(v)
    }
    v.exit()
}
