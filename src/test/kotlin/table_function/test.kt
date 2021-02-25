@file:Suppress("DEPRECATION")

package table_function

import junit.framework.Assert.*
import org.junit.Assert.assertThrows
import org.junit.Test

class Tests {

    @Test
    fun add() {
        val function = TableFunction()
        assertEquals(0, function.size)
        function.add(2.0, 3.0)
        assertEquals(1, function.size)
        function.add(5.0, 2.0)
        assertTrue(function.add(6.0, 6.0))
        assertFalse(function.add(5.0, 7.0))
        assertEquals(3, function.size)
    }

    @Test
    fun remove() {
        val function = TableFunction()
        assertFalse(function.remove(0.0))
        function.add(10.0, 15.0)
        function.add(21.0, 22.0)
        assertTrue(function.remove(21.0))
        assertEquals(1, function.size)
        assertFalse(function.remove(211.0))
    }

    @Test
    fun getPairs() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        val pairs = function.getPairs()
        assertEquals(1, pairs.size)
        assertEquals(1.0 to 2.0, pairs.single())
    }

    @Test
    fun findPair() {
        val function = TableFunction()
        function.add(1.0, 2.0)
        function.add(3.0, 4.0)
        function.add(5.0, 6.0)
        assertEquals(1.0 to 2.0, function.findPair(2.0))
        assertEquals(3.0 to 4.0, function.findPair(4.0))
        assertEquals(5.0 to 6.0, function.findPair(5.75))
        assertEquals(1.0 to 2.0, function.findPair(1.5))
    }

    @Test
    fun getValue() {
        val function = TableFunction()
        assertThrows(IllegalStateException::class.java) { function.getValue(125.0) }
        function.add(2.0, 4.0)
        assertEquals(4.0, function.getValue(35.0))
        function.add(6.0, 8.0)
        assertEquals(8.0, function.getValue(6.0), 1e-10)
        function.add(10.0, 12.0)
        assertEquals(2.0, function.getValue(0.0), 1e-10)
        assertEquals(14.0, function.getValue(12.0), 1e-10)
        assertEquals(10.0, function.getValue(8.0), 1e-10)
    }

    @Test
    fun equals() {
        val f1 = TableFunction()
        f1.add(1.0, 1.0)
        f1.add(3.0, 3.0)
        f1.add(5.0, 5.0)
        val f2 = TableFunction()
        f2.add(3.0, 3.0)
        f2.add(5.0, 5.0)
        f2.add(1.0, 1.0)
        assertTrue(f1 == f2)
        assertEquals(f1, f2)
    }
}