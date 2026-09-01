// port-lint: source impls/std/primitive.rs
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

// Rust impls Allocative directly on primitive types. Kotlin can't extend
// existing primitives with an interface, so the upstream `impl Allocative for
// uN/iN/bool/fN` set is rendered as faithful wrapper value classes. Each
// wrapper holds the underlying primitive and reports a simple sized self to
// the visitor, matching `visit_simple_sized::<Self>()` in the Rust source.
// Wrapper classes intentionally mirror the project's existing `ManuallyDrop`
// wrapper style in `Mem.kt`.

public class AllocativeByte(
    public val value: Byte,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeByte::class, Byte.SIZE_BYTES)
    }
}

public class AllocativeUByte(
    public val value: UByte,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeUByte::class, UByte.SIZE_BYTES)
    }
}

public class AllocativeShort(
    public val value: Short,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeShort::class, Short.SIZE_BYTES)
    }
}

public class AllocativeUShort(
    public val value: UShort,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeUShort::class, UShort.SIZE_BYTES)
    }
}

public class AllocativeInt(
    public val value: Int,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeInt::class, Int.SIZE_BYTES)
    }
}

public class AllocativeUInt(
    public val value: UInt,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeUInt::class, UInt.SIZE_BYTES)
    }
}

public class AllocativeLong(
    public val value: Long,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeLong::class, Long.SIZE_BYTES)
    }
}

public class AllocativeULong(
    public val value: ULong,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeULong::class, ULong.SIZE_BYTES)
    }
}

public class AllocativeFloat(
    public val value: Float,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeFloat::class, Float.SIZE_BYTES)
    }
}

public class AllocativeDouble(
    public val value: Double,
) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimpleSized(AllocativeDouble::class, Double.SIZE_BYTES)
    }
}

public class AllocativeBoolean(
    public val value: Boolean,
) : Allocative {
    override fun visit(visitor: Visitor) {
        // Boolean has no `SIZE_BYTES` constant in the Kotlin stdlib; on every
        // currently-supported target the JVM/Native ABI encodes a Boolean
        // field as one byte. Use 1 explicitly.
        visitor.visitSimpleSized(AllocativeBoolean::class, 1)
    }
}

// Rust's `usize` and `isize` are architecture-dependent (4 bytes on 32-bit
// targets, 8 on 64-bit). Kotlin has no language-level analogue: Int and Long
// are fixed-width. Faithful porting would need per-target actual size
// constants, but no public Allocative caller in this repo currently exposes
// usize/isize-typed fields. Leaving unported with this honest note rather
// than guessing a size; revisit when an actual caller needs it.
