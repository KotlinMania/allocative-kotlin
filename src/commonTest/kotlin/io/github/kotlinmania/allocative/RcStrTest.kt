// port-lint: tests allocative/src/rc_str.rs
package io.github.kotlinmania.allocative

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RcStrTest {
    @Test
    fun testRcStrBasic() {
        val s1 = RcStr.from("hello")
        val s2 = RcStr.from("hello")
        val s3 = RcStr.from("world")
        val default = RcStr.default()

        assertEquals("hello", s1.asStr())
        assertEquals("hello", s1.toString())
        assertEquals(5, s1.length)
        assertEquals('h', s1[0])
        assertEquals(s1, s2)
        assertEquals(s1.hashCode(), s2.hashCode())
        assertTrue(s1 < s3)
        assertEquals("", default.asStr())
    }
}
