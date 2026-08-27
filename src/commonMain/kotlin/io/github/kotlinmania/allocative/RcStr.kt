// port-lint: source rc_str.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

public class RcStr(
    private val value: String,
) : Comparable<RcStr>,
    CharSequence {
    override val length: Int
        get() = value.length

    override fun get(index: Int): Char = value[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        value.subSequence(startIndex, endIndex)

    public fun asStr(): String = value

    override fun compareTo(other: RcStr): Int = value.compareTo(other.value)

    override fun equals(other: Any?): Boolean =
        this === other || (other is RcStr && value == other.value)

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value

    public companion object {
        public fun from(s: String): RcStr = RcStr(s)

        public fun default(): RcStr = RcStr("")
    }
}
