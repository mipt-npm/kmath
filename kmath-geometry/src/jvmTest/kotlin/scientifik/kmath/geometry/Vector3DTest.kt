package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.structures.asSequence

internal class Vector3DTest {
    private val vector = Vector3D(1.0, -7.999, 0.001)

    @Test
    fun getSize() {
        assertEquals(3, vector.size)
    }

    @Test
    fun get() {
        assertEquals(1.0, vector[0])
        assertEquals(-7.999, vector[1])
        assertEquals(0.001, vector[2])
    }

    @Test
    operator fun iterator() {
        assertEquals(listOf(1.0, -7.999, 0.001), vector.asSequence().toList())
    }

    @Test
    fun getX() {
        assertEquals(1.0, vector.x)
    }

    @Test
    fun getY() {
        assertEquals(-7.999, vector.y)
    }

    @Test
    fun getZ() {
        assertEquals(0.001, vector.z)
    }
}