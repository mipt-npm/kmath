package scientifik.kmath.geometry

import kotlin.test.assertEquals
import kotlin.test.Test
import scientifik.kmath.structures.asSequence

internal class Vector2DTest {
    private val vector = Vector2D(1.0, -7.999)

    @Test
    fun getSize() {
        assertEquals(2, vector.size)
    }

    @Test
    fun get() {
        assertEquals(1.0, vector[0])
        assertEquals(-7.999, vector[1])
    }

    @Test
    operator fun iterator() {
        assertEquals(listOf(1.0, -7.999), vector.asSequence().toList())
    }

    @Test
    fun getX() {
        assertEquals(1.0, vector.x)
    }

    @Test
    fun getY() {
        assertEquals(-7.999, vector.y)
    }
}
