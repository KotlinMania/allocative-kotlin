// port-lint: source allocative/src/global_root.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

private val registeredRoots: MutableList<Allocative> = mutableListOf()

/**
 * Register global root which can be later traversed by profiler.
 */
public fun registerRoot(root: Allocative) {
    registeredRoots.add(root)
}

internal fun roots(): List<Allocative> = registeredRoots.toList()
