// port-lint: tests global_root.rs
package io.github.kotlinmania.allocative

import io.github.kotlinmania.allocative.key.Key
import kotlin.test.Test
import kotlin.test.assertTrue

class GlobalRootTest {
    private class TestGlobalRoot(
        val x: UInt,
    ) : Allocative {
        override fun visit(visitor: Visitor) {
            val self = visitor.enterSelf(this, 0)
            val field = self.enter(Key.new("x"), 0)
            field.visitSimple(Key.new("u32"), 4)
            field.exit()
            self.exit()
        }
    }

    @Test
    fun testDerive() {
        val root = TestGlobalRoot(17u)
        registerRoot(root)
        val fg = FlameGraphBuilder()
        fg.visitGlobalRoots()
        val flamegraph = fg.finishAndWriteFlameGraph()
        assertTrue(
            flamegraph.contains("TestGlobalRoot;x;u32 4"),
            "Expected flamegraph to contain 'TestGlobalRoot;x;u32 4', got: $flamegraph",
        )
    }
}
