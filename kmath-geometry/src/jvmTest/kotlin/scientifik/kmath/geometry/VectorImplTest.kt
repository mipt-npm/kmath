package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.*
import scientifik.kmath.structures.asSequence

internal class VectorImplTest {
    @Test
    fun getSize() {
        assertEquals(0, VectorImpl<Int, D0>(emptyList()).size)
        assertEquals(1, VectorImpl<Int, D1>(listOf(1)).size)
        assertEquals(2, VectorImpl<Int, D2>(listOf(1, 2)).size)
        assertEquals(3, VectorImpl<Int, D3>(listOf(1, 2, 3)).size)
        assertEquals(8, VectorImpl<Int, D8>(listOf(1, 2, 3, 4, 5, 6, 7, 8)).size)
    }

    @Test
    fun get() {
        val v1 = VectorImpl<Int, D1>(listOf(100))
        assertEquals(100, v1[0])
        val v2 = VectorImpl<Int, D2>(listOf(2, 3))
        assertEquals(2, v2[0])
        assertEquals(3, v2[1])
        val v8 = VectorImpl<Int, D8>(listOf(0, 1, 2, 3, 4, 5, 6, 7))
        assertEquals(0, v8[0])
        assertEquals(1, v8[1])
        assertEquals(2, v8[2])
        assertEquals(3, v8[3])
        assertEquals(4, v8[4])
        assertEquals(5, v8[5])
        assertEquals(6, v8[6])
        assertEquals(7, v8[7])
    }

    @Test
    fun iterator() {
        assertEquals(emptyList<Int>(), VectorImpl<Int, D0>(emptyList()).asSequence().toList())
        assertEquals(listOf(1), VectorImpl<Int, D1>(listOf(1)).asSequence().toList())
        assertEquals(listOf(1, 2), VectorImpl<Int, D2>(listOf(1, 2)).asSequence().toList())
        assertEquals(listOf(1, 2, 3), VectorImpl<Int, D3>(listOf(1, 2, 3)).asSequence().toList())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8), VectorImpl<Int, D8>(listOf(1, 2, 3, 4, 5, 6, 7, 8)).asSequence().toList())
    }

    @Test
    fun vectorOfHelper() {
        assertEquals(VectorImpl<Int, D1>(listOf(1)), vectorOf(1))
        assertEquals(VectorImpl<Int, D2>(listOf(1, 2)), vectorOf(1, 2))
        assertEquals(VectorImpl<Int, D3>(listOf(1, 2, 3)), vectorOf(1, 2, 3))
        assertEquals(VectorImpl<Int, D5>(listOf(1, 2, 3, 4, 5)), vectorOf(1, 2, 3, 4, 5))
    }
}