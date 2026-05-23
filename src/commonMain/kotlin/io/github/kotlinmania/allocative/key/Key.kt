// port-lint: source key.rs
package io.github.kotlinmania.allocative.key

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

/** Hashed string, which is a key while descending into a tree (e.g. type name or field name). */
public class Key internal constructor(
    internal val hash: ULong,
    public val s: String,
) : Comparable<Key> {

    override fun equals(other: Any?): Boolean =
        this === other || (other is Key && hash == other.hash && s == other.s)

    override fun hashCode(): Int = hash.toLong().hashCode()

    override fun compareTo(other: Key): Int = s.compareTo(other.s)

    override fun toString(): String = "Key { hash: $hash, s: \"$s\" }"

    public companion object {
        /** Must be identical to `allocativeDerive.hash`. */
        private fun computeHash(s: String): ULong {
            var hash: ULong = 0xcbf29ce484222325uL
            val bytes = s.encodeToByteArray()
            var i = 0
            while (i < bytes.size) {
                val b = bytes[i]
                hash = hash xor b.toUByte().toULong()
                hash = hash * 0x100000001b3uL
                i += 1
            }
            return hash
        }

        /** Compute hash. */
        public fun new(s: String): Key {
            val hash = computeHash(s)
            return newUnchecked(hash, s)
        }

        public fun newUnchecked(hash: ULong, s: String): Key = Key(hash, s)

        public inline fun <reified T : Any> forTypeName(): Key {
            val name = T::class.simpleName ?: T::class.toString()
            return new(name)
        }

        public fun forTypeName(type: kotlin.reflect.KClass<*>): Key {
            val name = type.simpleName ?: type.toString()
            return new(name)
        }
    }
}
