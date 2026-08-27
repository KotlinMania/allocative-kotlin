// port-lint: source allocative/src/impls/serde_json.rs
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

public class AllocativeJsonNumber(
    public val value: Double,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.enterSelfSized(AllocativeJsonNumber::class, Double.SIZE_BYTES).exit()
    }
}

public class AllocativeJsonString(
    public val value: String,
) : Allocative {
    override fun visit(visitor: Visitor) {
        val self = visitor.enterSelf(this, value.length)
        self.exit()
    }
}

public sealed class AllocativeJsonValue : Allocative {
    public object Null : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 0)
            self.exit()
        }
    }

    public class Bool(
        public val value: Boolean,
    ) : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 1)
            self.exit()
        }
    }

    public class Number(
        public val number: AllocativeJsonNumber,
    ) : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 8)
            self.visitField(KEY_NUMBER, 8, number)
            self.exit()
        }
    }

    public class Str(
        public val string: AllocativeJsonString,
    ) : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, string.value.length)
            self.visitField(KEY_STRING, string.value.length, string)
            self.exit()
        }
    }

    public class Array(
        public val elements: List<AllocativeJsonValue>,
    ) : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, elements.size * 8)
            for (elem in elements) {
                self.visitField(KEY_ARRAY, 8, elem)
            }
            self.exit()
        }
    }

    public class Obj(
        public val entries: Map<String, AllocativeJsonValue>,
    ) : AllocativeJsonValue() {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, entries.size * 16)
            for ((k, v) in entries) {
                self.visitField(Key.new(k), 8, v)
            }
            self.exit()
        }
    }

    private companion object {
        val KEY_NUMBER: Key = Key.new("Number")
        val KEY_STRING: Key = Key.new("String")
        val KEY_ARRAY: Key = Key.new("Array")
    }
}
