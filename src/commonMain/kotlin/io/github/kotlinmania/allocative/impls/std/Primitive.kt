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

// Primitive types are represented as wrapper value classes. Each wrapper
// holds the underlying primitive and reports a simple sized self to the
// visitor, matching the upstream primitive implementations.

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

public fun Byte.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Byte::class, Byte.SIZE_BYTES)
}

public fun UByte.visit(visitor: Visitor) {
    visitor.visitSimpleSized(UByte::class, UByte.SIZE_BYTES)
}

public fun Short.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Short::class, Short.SIZE_BYTES)
}

public fun UShort.visit(visitor: Visitor) {
    visitor.visitSimpleSized(UShort::class, UShort.SIZE_BYTES)
}

public fun Int.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Int::class, Int.SIZE_BYTES)
}

public fun UInt.visit(visitor: Visitor) {
    visitor.visitSimpleSized(UInt::class, UInt.SIZE_BYTES)
}

public fun Long.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Long::class, Long.SIZE_BYTES)
}

public fun ULong.visit(visitor: Visitor) {
    visitor.visitSimpleSized(ULong::class, ULong.SIZE_BYTES)
}

public fun Float.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Float::class, Float.SIZE_BYTES)
}

public fun Double.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Double::class, Double.SIZE_BYTES)
}

public fun Boolean.visit(visitor: Visitor) {
    visitor.visitSimpleSized(Boolean::class, 1)
}
