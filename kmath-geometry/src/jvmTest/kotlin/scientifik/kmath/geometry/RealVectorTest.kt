package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.*
import scientifik.kmath.structures.asSequence

internal class RealVectorTest {
    @Test
    fun getSize() {
        assertEquals(0, RealVector<D0>(doubleArrayOf()).size)
        assertEquals(1, RealVector<D1>(doubleArrayOf(1.0)).size)
        assertEquals(2, RealVector<D2>(doubleArrayOf(1.0, 2.0)).size)
        assertEquals(3, RealVector<D3>(doubleArrayOf(1.0, 2.0, 3.0)).size)
        assertEquals(5, RealVector<D5>(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0)).size)
        assertEquals(8, RealVector<D8>(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)).size)
    }

    @Test
    fun get() {
        val v1 = RealVector<D1>(doubleArrayOf(100.0))
        assertEquals(100.0, v1[0])
        val v2 = RealVector<D2>(doubleArrayOf(2.0, 3.0))
        assertEquals(2.0, v2[0])
        assertEquals(3.0, v2[1])
        val v8 = RealVector<D8>(doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0))
        assertEquals(0.0, v8[0])
        assertEquals(1.0, v8[1])
        assertEquals(2.0, v8[2])
        assertEquals(3.0, v8[3])
        assertEquals(4.0, v8[4])
        assertEquals(5.0, v8[5])
        assertEquals(6.0, v8[6])
        assertEquals(7.0, v8[7])
    }

    @Test
    fun iterator() {
        assertEquals(emptyList<Double>(), RealVector<D0>(doubleArrayOf()).asSequence().toList())
        assertEquals(listOf(1.0), RealVector<D1>(doubleArrayOf(1.0)).asSequence().toList())
        assertEquals(listOf(1.0, 2.0), RealVector<D2>(doubleArrayOf(1.0, 2.0)).asSequence().toList())
        assertEquals(listOf(1.0, 2.0, 3.0), RealVector<D3>(doubleArrayOf(1.0, 2.0, 3.0)).asSequence().toList())
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0), RealVector<D8>(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)).asSequence().toList())
    }

    @Test
    fun vectorOfHelper() {
        assertEquals(RealVector<D1>(doubleArrayOf(1.0)), vectorOf(1.0))
        assertEquals(RealVector<D2>(doubleArrayOf(1.0, 2.0)), vectorOf(1.0, 2.0))
        assertEquals(RealVector<D3>(doubleArrayOf(1.0, 2.0, 3.0)), vectorOf(1.0, 2.0, 3.0))
        assertEquals(RealVector<D5>(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0)), vectorOf(1.0, 2.0, 3.0, 4.0, 5.0))
    }
}