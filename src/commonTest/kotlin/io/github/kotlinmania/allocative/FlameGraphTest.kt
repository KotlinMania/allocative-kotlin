// port-lint: tests flamegraph.rs
package io.github.kotlinmania.allocative

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

import io.github.kotlinmania.allocative.key.Key
import kotlin.test.Test
import kotlin.test.assertEquals

class FlameGraphTest {
    @Test
    fun testEmpty() {
        val fg = FlameGraphBuilder()
        fg.rootVisitor().exit()
        val tree = fg.finishImpl()
        assertEquals("", tree.toFlameGraph().first.write())
    }

    @Test
    fun testSimple() {
        val fg = FlameGraphBuilder()
        val visitor = fg.rootVisitor()
        visitor.visitSimple(Key.new("a"), 10)
        visitor.exit()
        val tree = fg.finishImpl()
        assertEquals("a 10\n", tree.toFlameGraph().first.write())
    }

    @Test
    fun testUnique() {
        val fg = FlameGraphBuilder()
        val visitor = fg.rootVisitor()
        val s = visitor.enter(Key.new("Struct"), 10)
        s.visitSimple(Key.new("a"), 3)
        val un = s.enterUnique(Key.new("p"), 6)
        un.visitSimple(Key.new("x"), 13)
        un.exit()
        s.exit()
        visitor.exit()

        val tree = fg.finishImpl()
        val expected =
            "Struct 1\n" +
                "Struct;a 3\n" +
                "Struct;p 6\n" +
                "Struct;p;x 13\n"
        assertEquals(expected, tree.toFlameGraph().first.write())
    }

    @Test
    fun testShared() {
        val p = Any()

        val fg = FlameGraphBuilder()
        val visitor = fg.rootVisitor()

        for (i in 0 until 2) {
            val s = visitor.enter(Key.new("Struct"), 10)
            s.visitSimple(Key.new("a"), 3)
            val sh = s.enterShared(Key.new("p"), 6, p)
            if (sh != null) {
                sh.visitSimple(Key.new("Shared"), 13)
                sh.exit()
            }
            s.exit()
        }

        visitor.exit()

        val tree = fg.finishImpl()
        val expected =
            "Shared 13\n" +
                "Struct 2\n" +
                "Struct;a 6\n" +
                "Struct;p 12\n"
        assertEquals(expected, tree.toFlameGraph().first.write())
    }

    @Test
    fun testInlineChildrenTooLarge() {
        val fg = FlameGraphBuilder()
        val visitor = fg.rootVisitor()
        val childVisitor = visitor.enter(Key.new("a"), 10)
        childVisitor.visitSimple(Key.new("b"), 13)
        childVisitor.exit()
        visitor.exit()
        val output = fg.finish()
        assertEquals("a;b 13\n", output.flamegraph.write())
        assertEquals(
            "Incorrect size declaration for node `a`, size of self: 10, size of inline children: 13\n",
            output.warnings,
        )
    }

    @Test
    fun testFlamegraphAdd() {
        val a = FlameGraph()

        val b1 = FlameGraph()
        b1.addSelf(10)

        val b = FlameGraph()
        b.addChild(Key.new("x"), b1)

        a.add(b)

        val b2 = FlameGraph()
        b2.addSelf(20)

        val b3 = FlameGraph()
        b3.addChild(Key.new("x"), b2)

        a.add(b3)

        assertEquals("x 30\n", a.write())
    }
}
