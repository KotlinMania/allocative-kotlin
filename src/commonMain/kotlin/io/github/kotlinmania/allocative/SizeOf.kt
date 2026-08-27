// port-lint: source allocative/src/size_of.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.key.Key

/**
 * Size of data allocated in unique pointers in the struct.
 *
 * * Exclude self
 * * Exclude shared pointers
 * * For unique pointers, include the size of the pointee plus this function recursively
 */
public fun sizeOfUniqueAllocatedData(root: Allocative): Int {
    class SizeOfUniqueAllocatedDataVisitor : VisitorImpl {
        var size: Int = 0

        override fun enterInlineImpl(name: Key, size: Int, parent: NodeKind) {
            name.hashCode()
            if (parent == NodeKind.Unique) {
                this.size += size
            }
        }

        override fun enterUniqueImpl(name: Key, size: Int, parent: NodeKind) {
            name.hashCode()
            size.hashCode()
            parent.hashCode()
        }

        override fun enterSharedImpl(
            name: Key,
            size: Int,
            sharedIdentity: Any,
            parent: NodeKind,
        ): Boolean {
            name.hashCode()
            size.hashCode()
            sharedIdentity.hashCode()
            parent.hashCode()
            return false
        }

        override fun exitInlineImpl() {}

        override fun exitUniqueImpl() {}

        override fun exitSharedImpl() {
            error("shared pointers are not visited")
        }

        override fun exitRootImpl() {}
    }

    val visitorImpl = SizeOfUniqueAllocatedDataVisitor()
    val visitor = Visitor(visitorImpl, NodeKind.Root)
    root.visit(visitor)
    visitor.exit()
    return visitorImpl.size
}

/**
 * Size of a piece of data and data allocated in unique pointers in the struct.
 *
 * * Excludes shared pointers
 */
public fun sizeOfUnique(root: Allocative, selfSize: Int): Int = selfSize + sizeOfUniqueAllocatedData(root)
