// port-lint: tests allocative/src/impls/std/primitive.rs
package io.github.kotlinmania.allocative.impls.std

import io.github.kotlinmania.allocative.Allocative
import io.github.kotlinmania.allocative.NodeKind
import io.github.kotlinmania.allocative.Visitor
import io.github.kotlinmania.allocative.VisitorImpl
import io.github.kotlinmania.allocative.key.Key
import kotlin.test.Test
import kotlin.test.assertEquals

private class RecordingVisitorImpl : VisitorImpl {
    var lastSize: Int = -1
    var enterCount: Int = 0
    var exitCount: Int = 0

    override fun enterInlineImpl(name: Key, size: Int, parent: NodeKind) {
        lastSize = size
        enterCount++
    }

    override fun enterUniqueImpl(name: Key, size: Int, parent: NodeKind) {
        lastSize = size
        enterCount++
    }

    override fun enterSharedImpl(name: Key, size: Int, sharedIdentity: Any, parent: NodeKind): Boolean {
        lastSize = size
        enterCount++
        return true
    }

    override fun exitInlineImpl() {
        exitCount++
    }

    override fun exitUniqueImpl() {
        exitCount++
    }

    override fun exitSharedImpl() {
        exitCount++
    }

    override fun exitRootImpl() {
        exitCount++
    }
}

private fun record(value: Allocative): RecordingVisitorImpl {
    val impl = RecordingVisitorImpl()
    val v = Visitor(impl, NodeKind.Root)
    value.visit(v)
    return impl
}

class PrimitiveTest {
    @Test fun byteVisitsOneByte() {
        val r = record(AllocativeByte(42))
        assertEquals(1, r.lastSize)
        assertEquals(1, r.enterCount)
        assertEquals(1, r.exitCount)
    }

    @Test fun uByteVisitsOneByte() {
        val r = record(AllocativeUByte(42u))
        assertEquals(1, r.lastSize)
    }

    @Test fun shortVisitsTwoBytes() {
        val r = record(AllocativeShort(7))
        assertEquals(2, r.lastSize)
    }

    @Test fun uShortVisitsTwoBytes() {
        val r = record(AllocativeUShort(7u))
        assertEquals(2, r.lastSize)
    }

    @Test fun intVisitsFourBytes() {
        val r = record(AllocativeInt(123_456))
        assertEquals(4, r.lastSize)
    }

    @Test fun uIntVisitsFourBytes() {
        val r = record(AllocativeUInt(123_456u))
        assertEquals(4, r.lastSize)
    }

    @Test fun longVisitsEightBytes() {
        val r = record(AllocativeLong(1L shl 40))
        assertEquals(8, r.lastSize)
    }

    @Test fun uLongVisitsEightBytes() {
        val r = record(AllocativeULong(1uL shl 40))
        assertEquals(8, r.lastSize)
    }

    @Test fun floatVisitsFourBytes() {
        val r = record(AllocativeFloat(1.5f))
        assertEquals(4, r.lastSize)
    }

    @Test fun doubleVisitsEightBytes() {
        val r = record(AllocativeDouble(1.5))
        assertEquals(8, r.lastSize)
    }

    @Test fun booleanVisitsOneByte() {
        val r = record(AllocativeBoolean(true))
        assertEquals(1, r.lastSize)
    }

    @Test fun wrappersExposeOriginalValue() {
        assertEquals(7.toByte(), AllocativeByte(7).value)
        assertEquals(7, AllocativeInt(7).value)
        assertEquals(true, AllocativeBoolean(true).value)
        assertEquals(1.5, AllocativeDouble(1.5).value)
    }
}
