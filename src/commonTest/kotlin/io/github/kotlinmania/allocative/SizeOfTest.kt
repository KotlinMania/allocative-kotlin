// port-lint: tests allocative/src/size_of.rs
package io.github.kotlinmania.allocative

import io.github.kotlinmania.allocative.key.Key
import kotlin.test.Test
import kotlin.test.assertEquals

class SizeOfTest {
    private class BoxedU32(
        val data: UInt,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 8)
            val unique = self.enterUnique(Key.new("data"), 0)
            unique.visitSimple(Key.new("u32"), 4)
            unique.exit()
            self.exit()
        }
    }

    private class BoxedSlice(
        val data: List<UInt>,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 8)
            val unique = self.enterUnique(Key.new("data"), 0)
            for (i in data.indices) {
                unique.visitSimple(Key.new("u32"), 4)
            }
            unique.exit()
            self.exit()
        }
    }

    private class Data(
        val a: UByte,
        val b: UInt,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 8)
            self.visitSimple(Key.new("a"), 1)
            val unique = self.enterUnique(Key.new("b"), 0)
            unique.visitSimple(Key.new("u32"), 4)
            unique.exit()
            self.exit()
        }
    }

    private class BoxedData(
        val data: Data,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 8)
            val unique = self.enterUnique(Key.new("data"), 0)
            data.visit(unique)
            unique.exit()
            self.exit()
        }
    }

    @Test
    fun testBox() {
        val boxed = BoxedU32(17u)
        assertEquals(4, sizeOfUniqueAllocatedData(boxed))
        assertEquals(4 + 8, sizeOfUnique(boxed, 8))
    }

    @Test
    fun testBoxSlice() {
        val boxed = BoxedSlice(listOf(1u, 2u, 3u))
        assertEquals(4 * 3, sizeOfUniqueAllocatedData(boxed))
        assertEquals(8 + 4 * 3, sizeOfUnique(boxed, 8))
    }

    @Test
    fun testStructInBox() {
        val boxed = BoxedData(Data(1u, 2u))
        assertEquals(8 + 4, sizeOfUniqueAllocatedData(boxed))
        assertEquals(8 + 8 + 4, sizeOfUnique(boxed, 8))
    }
}
