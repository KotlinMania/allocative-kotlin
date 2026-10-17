// port-lint: source impls/tokio.rs
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
import io.github.kotlinmania.tokio.util.SyncWrapper
import kotlinx.coroutines.sync.Mutex

private val KEY_DATA: Key = Key.new("data")

public fun <T : Allocative> SyncWrapper<T>.visit(
    visitor: Visitor,
    value: T?,
    selfSizeBytes: Int = 8,
    dataSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    if (value != null) {
        v.visitField(KEY_DATA, dataSizeBytes, value)
    }
    v.exit()
}

public fun <T : Allocative> Mutex.visit(
    data: T,
    visitor: Visitor,
    selfSizeBytes: Int = 8,
    dataSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    if (tryLock()) {
        try {
            v.visitField(KEY_DATA, dataSizeBytes, data)
        } finally {
            unlock()
        }
    }
    v.exit()
}
