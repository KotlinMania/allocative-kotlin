// port-lint: source impls/serde_json.rs
package io.github.kotlinmania.allocative.impls

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.Visitor
import io.github.kotlinmania.allocative.impls.std.visit
import io.github.kotlinmania.allocative.key.Key
import io.github.kotlinmania.serdejson.JsonMap
import io.github.kotlinmania.serdejson.JsonNumber
import io.github.kotlinmania.serdejson.Value

private val KEY_DATA: Key = Key.new("data")
private val KEY_KEY: Key = Key.new("key")
private val KEY_VALUE: Key = Key.new("value")
private val KEY_NUMBER: Key = Key.new("Number")
private val KEY_STRING: Key = Key.new("String")
private val KEY_ARRAY: Key = Key.new("Array")
private val KEY_OBJECT: Key = Key.new("Object")

public fun JsonNumber.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    // Number may have allocations, but it is opaque.
    v.exit()
}

public fun JsonMap.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
    pointerSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    val dataVisitor = v.enterUnique(KEY_DATA, pointerSizeBytes)
    for ((k, value) in iter()) {
        dataVisitor.visitFieldWith(KEY_KEY, pointerSizeBytes) { child ->
            k.visit(child, selfSizeBytes, pointerSizeBytes)
        }
        dataVisitor.visitFieldWith(KEY_VALUE, pointerSizeBytes) { child ->
            value.visit(child, selfSizeBytes, pointerSizeBytes)
        }
    }
    dataVisitor.exit()
    v.exit()
}

public fun Value.visit(
    visitor: Visitor,
    selfSizeBytes: Int = 8,
    pointerSizeBytes: Int = 8,
) {
    val v = visitor.enterSelf(this, selfSizeBytes)
    when (this) {
        is Value.Null -> {}

        is Value.Bool -> {}

        is Value.Number -> {
            v.visitFieldWith(KEY_NUMBER, selfSizeBytes) { child ->
                value.visit(child, selfSizeBytes)
            }
        }

        is Value.Str -> {
            v.visitFieldWith(KEY_STRING, pointerSizeBytes) { child ->
                value.visit(child, selfSizeBytes, pointerSizeBytes)
            }
        }

        is Value.Array -> {
            v.visitFieldWith(KEY_ARRAY, pointerSizeBytes) { child ->
                val listVisitor = child.enter(CAPACITY_NAME, pointerSizeBytes * value.size)
                for (elem in value) {
                    elem.visit(listVisitor, selfSizeBytes, pointerSizeBytes)
                }
                listVisitor.exit()
            }
        }

        is Value.Object -> {
            v.visitFieldWith(KEY_OBJECT, pointerSizeBytes) { child ->
                value.visit(child, selfSizeBytes, pointerSizeBytes)
            }
        }
    }
    v.exit()
}
