package io.github.kotlinmania.allocative.impls

import kotlin.test.Test
import kotlin.test.assertEquals

class HashbrownUtilTest {
    @Test
    fun computesRawTableAllocationBuckets() {
        assertEquals(0, rawTableAllocSizeForLen(len = 0, elementSizeBytes = 8))
        assertEquals(36, rawTableAllocSizeForLen(len = 1, elementSizeBytes = 8))
        assertEquals(36, rawTableAllocSizeForLen(len = 3, elementSizeBytes = 8))
        assertEquals(36, rawTableAllocSizeForLen(len = 4, elementSizeBytes = 8))
        assertEquals(72, rawTableAllocSizeForLen(len = 5, elementSizeBytes = 8))
    }
}
