// port-lint: source flamegraph.rs
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

/**
 * Node in flamegraph tree.
 *
 * Can be written to flamegraph format with [write].
 */
public class FlameGraph internal constructor(
    internal val children: MutableMap<Key, FlameGraph>,
    internal var childrenSize: Int,
    internal var nodeSize: Int,
) {
    public constructor() : this(mutableMapOf(), 0, 0)

    public fun totalSize(): Int = nodeSize + childrenSize

    /** Add another flamegraph to this one. */
    public fun add(other: FlameGraph) {
        nodeSize += other.nodeSize
        for ((key, child) in other.children) {
            addChild(key, child)
        }
    }

    /** Add a child node to the flamegraph, merging if it already exists. */
    public fun addChild(key: Key, child: FlameGraph) {
        childrenSize += child.totalSize()
        val existing = children[key]
        if (existing != null) {
            existing.add(child)
        } else {
            children[key] = child
        }
    }

    /** Add size to this node. */
    public fun addSelf(size: Int) {
        nodeSize += size
    }

    private fun writeFlameGraphImpl(stack: List<String>, w: StringBuilder) {
        if (nodeSize != 0) {
            if (stack.isNotEmpty()) {
                w
                    .append(stack.joinToString(";"))
                    .append(" ")
                    .append(nodeSize)
                    .append("\n")
            }
        }
        val sortedChildren = children.entries.sortedBy { it.key.s }
        for ((key, child) in sortedChildren) {
            child.writeFlameGraphImpl(stack + key.s, w)
        }
    }

    /**
     * Write flamegraph in format suitable for flamegraph.pl or inferno.
     */
    public fun write(): String {
        val r = StringBuilder()
        writeFlameGraphImpl(emptyList(), r)
        return r.toString()
    }
}

public class FlameGraphOutput internal constructor(
    public val flamegraph: FlameGraph,
    public val warnings: String,
)

internal class TreeData(
    var size: Int = 0,
    var remSize: Int = 0,
    var unique: Boolean = false,
    val children: MutableMap<Key, Int> = mutableMapOf(),
) {
    fun inlineChildrenSize(): Int = size - remSize
}

internal class TreeRef(
    private val trees: List<TreeData>,
    private val treeId: Int,
) {
    fun writeFlameGraph(stack: List<String>, warnings: StringBuilder): FlameGraph {
        val flameGraph = FlameGraph()
        val tree = trees[treeId]
        if (tree.remSize > 0) {
            if (stack.isNotEmpty()) {
                flameGraph.nodeSize = tree.remSize
            }
        } else if (tree.remSize < 0 && stack.isNotEmpty()) {
            warnings.append(
                "Incorrect size declaration for node `${stack.joinToString(";")}`, " +
                    "size of self: ${tree.size}, size of inline children: ${tree.inlineChildrenSize()}\n",
            )
        }
        val sortedChildren = tree.children.entries.sortedBy { it.key.s }
        for ((key, childId) in sortedChildren) {
            val childRef = TreeRef(trees, childId)
            val childFlameGraph = childRef.writeFlameGraph(stack + key.s, warnings)
            flameGraph.addChild(key, childFlameGraph)
        }
        return flameGraph
    }

    fun toFlameGraph(): Pair<FlameGraph, String> {
        val warnings = StringBuilder()
        val flameGraph = writeFlameGraph(emptyList(), warnings)
        return flameGraph to warnings.toString()
    }
}

internal class Tree(
    val trees: List<TreeData>,
    val treeId: Int,
) {
    fun toFlameGraph(): Pair<FlameGraph, String> = TreeRef(trees, treeId).toFlameGraph()
}

internal class TreeStack(
    val stack: MutableList<Int> = mutableListOf(),
    var tree: Int = 0,
) {
    fun copy(): TreeStack = TreeStack(stack.toMutableList(), tree)
}

internal class IdentityWrapper(
    val target: Any,
) {
    override fun equals(other: Any?): Boolean =
        other is IdentityWrapper && this.target === other.target

    override fun hashCode(): Int = target.hashCode()
}

/**
 * Build a flamegraph from given root objects.
 */
public class FlameGraphBuilder {
    private val visitedShared: MutableSet<IdentityWrapper> = mutableSetOf()
    private val trees: MutableList<TreeData> = mutableListOf()
    private var current: TreeStack
    private val shared: MutableList<TreeStack> = mutableListOf()
    private val root: Int
    private var enteredRootVisitor: Boolean = false

    private val visitorImpl =
        object : VisitorImpl {
            override fun enterInlineImpl(name: Key, size: Int, _parent: NodeKind) {
                down(name)
                trees[current.tree].size += size
            }

            override fun enterUniqueImpl(name: Key, size: Int, _parent: NodeKind) {
                down(name)
                trees[current.tree].size += size
                trees[current.tree].unique = true
            }

            override fun enterSharedImpl(
                name: Key,
                size: Int,
                sharedIdentity: Any,
                _parent: NodeKind,
            ): Boolean {
                down(name)
                trees[current.tree].size += size

                if (!visitedShared.add(IdentityWrapper(sharedIdentity))) {
                    exitImpl()
                    return false
                }

                shared.add(current.copy())
                current = TreeStack(mutableListOf(), root)
                return true
            }

            override fun exitInlineImpl() {
                exitImpl()
            }

            override fun exitUniqueImpl() {
                exitImpl()
            }

            override fun exitSharedImpl() {
                exitImpl()
            }

            override fun exitRootImpl() {
                exitImpl()
            }
        }

    init {
        root = newTree()
        current = TreeStack(mutableListOf(), root)
    }

    private fun newTree(): Int {
        val id = trees.size
        trees.add(TreeData())
        return id
    }

    public fun rootVisitor(): Visitor {
        check(!enteredRootVisitor) { "Root visitor already entered" }
        enteredRootVisitor = true
        return Visitor(visitorImpl, NodeKind.Root)
    }

    /** Collect tree sizes starting from given root. */
    public fun visitRoot(root: Allocative) {
        val visitor = rootVisitor()
        root.visit(visitor)
        visitor.exit()
    }

    /** Collect data from global roots registered with [registerRoot]. */
    public fun visitGlobalRoots() {
        for (root in roots()) {
            visitRoot(root)
        }
    }

    internal fun finishImpl(): Tree {
        check(shared.isEmpty())
        check(current.stack.isEmpty())
        check(!enteredRootVisitor)
        updateSizes(root, trees)
        return Tree(trees, root)
    }

    /** Finish building the flamegraph. */
    public fun finish(): FlameGraphOutput {
        val tree = finishImpl()
        val (flamegraph, warnings) = tree.toFlameGraph()
        return FlameGraphOutput(flamegraph, warnings)
    }

    /** Finish building the flamegraph and return the flamegraph output. */
    public fun finishAndWriteFlameGraph(): String = finish().flamegraph.write()

    private fun updateSizes(treeId: Int, trees: List<TreeData>) {
        val tree = trees[treeId]
        for (child in tree.children.values.toList()) {
            updateSizes(child, trees)
        }
        val childrenSize =
            if (tree.unique) {
                0
            } else {
                tree.children.values.sumOf { trees[it].size }
            }
        tree.remSize = tree.size - childrenSize
    }

    private fun down(key: Key) {
        current.stack.add(current.tree)
        val existing = trees[current.tree].children[key]
        val child =
            if (existing != null) {
                existing
            } else {
                val newId = newTree()
                trees[current.tree].children[key] = newId
                newId
            }
        current.tree = child
    }

    private fun up(): Boolean =
        if (current.stack.isNotEmpty()) {
            current.tree = current.stack.removeAt(current.stack.size - 1)
            true
        } else {
            false
        }

    private fun exitImpl() {
        check(enteredRootVisitor)
        val hasParent = up()
        if (!hasParent) {
            if (shared.isNotEmpty()) {
                current = shared.removeAt(shared.size - 1)
                check(up())
            } else {
                enteredRootVisitor = false
            }
        }
    }
}
