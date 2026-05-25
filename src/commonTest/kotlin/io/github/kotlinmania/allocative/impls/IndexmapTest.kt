// port-lint: tests impls/indexmap.rs
package io.github.kotlinmania.allocative.impls

import io.github.kotlinmania.allocative.Allocative
import io.github.kotlinmania.allocative.NodeKind
import io.github.kotlinmania.allocative.Visitor
import io.github.kotlinmania.allocative.VisitorImpl
import io.github.kotlinmania.allocative.key.Key
import io.github.kotlinmania.indexmap.IndexMap
import io.github.kotlinmania.indexmap.IndexSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class RecordingVisitorImpl : VisitorImpl {
    val entered = mutableListOf<Pair<String, Int>>()
    val exited = mutableListOf<NodeKind>()

    override fun enterInlineImpl(name: Key, size: Int, parent: NodeKind) {
        entered += name.s to size
    }

    override fun enterUniqueImpl(name: Key, size: Int, parent: NodeKind) {
        entered += name.s to size
    }

    override fun enterSharedImpl(name: Key, size: Int, sharedIdentity: Any, parent: NodeKind): Boolean {
        entered += name.s to size
        return true
    }

    override fun exitInlineImpl() {
        exited += NodeKind.Inline
    }

    override fun exitUniqueImpl() {
        exited += NodeKind.Unique
    }

    override fun exitSharedImpl() {
        exited += NodeKind.Shared
    }

    override fun exitRootImpl() {
        exited += NodeKind.Root
    }
}

private class SizedValue(private val label: String) : Allocative {
    override fun visit(visitor: Visitor) {
        visitor.visitSimple(Key.new(label), 1)
    }
}

private fun rootVisitor(impl: RecordingVisitorImpl): Visitor = Visitor(impl, NodeKind.Root)

class IndexmapTest {
    @Test
    fun visitsIndexSetValuesAndUnusedCapacity() {
        val set = IndexSet.withCapacity<SizedValue>(4)
        set.insert(SizedValue("a"))
        set.insert(SizedValue("b"))

        val impl = RecordingVisitorImpl()
        visitIndexSet(
            set = set,
            visitor = rootVisitor(impl),
            selfSizeBytes = 24,
            referenceSizeBytes = 8,
            elementSizeBytes = 16,
        )

        assertTrue(("IndexSet" to 24) in impl.entered)
        assertTrue(("data" to 8) in impl.entered)
        assertTrue(("value" to 16) in impl.entered)
        assertTrue(("unused_capacity" to 0) in impl.entered)
        assertTrue(impl.entered.any { it.first == "raw_table" })
    }

    @Test
    fun visitsIndexMapKeysValuesAndUnusedCapacity() {
        val map = IndexMap.withCapacity<SizedValue, SizedValue>(4)
        map.insert(SizedValue("k1"), SizedValue("v1"))
        map.insert(SizedValue("k2"), SizedValue("v2"))

        val impl = RecordingVisitorImpl()
        visitIndexMap(
            map = map,
            visitor = rootVisitor(impl),
            selfSizeBytes = 32,
            referenceSizeBytes = 8,
            keySizeBytes = 16,
            valueSizeBytes = 24,
        )

        assertTrue(("IndexMap" to 32) in impl.entered)
        assertTrue(("data" to 8) in impl.entered)
        assertEquals(2, impl.entered.count { it == ("key" to 16) })
        assertEquals(2, impl.entered.count { it == ("value" to 24) })
        assertTrue(("unused_capacity" to 0) in impl.entered)
        assertTrue(impl.entered.any { it.first == "raw_table" })
    }
}
