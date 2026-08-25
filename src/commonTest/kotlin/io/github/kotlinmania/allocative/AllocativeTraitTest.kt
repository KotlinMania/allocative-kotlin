// port-lint: tests allocative_trait.rs
package io.github.kotlinmania.allocative

import io.github.kotlinmania.allocative.key.Key
import kotlin.test.Test
import kotlin.test.assertEquals

class AllocativeTraitTest {
    private class SimpleNode(
        val size: Int,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, size)
            self.visitSimple(Key.new("size"), 4)
            self.exit()
        }
    }

    @Test
    fun testAllocativeInterface() {
        val node = SimpleNode(16)
        assertEquals(0, sizeOfUniqueAllocatedData(node))
        assertEquals(16, sizeOfUnique(node, 16))
    }
}
