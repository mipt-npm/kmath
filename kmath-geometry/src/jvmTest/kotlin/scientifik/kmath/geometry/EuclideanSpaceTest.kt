package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.*
import scientifik.kmath.operations.FloatField
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.invoke

internal class EuclideanSpaceTest {
    @Test
    fun distance() {
        (EuclideanSpace(D3, RealField)) {
            assertEquals(0.0, distance(zero, zero))
            assertEquals(1.0, distance(zero, vectorOf(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(5.000001), distance(vectorOf(1.0, -2.0, 0.001), zero))
            assertEquals(0.0, distance(vectorOf(1.0, -2.0, 0.001), vectorOf(1.0, -2.0, 0.001)))
            assertEquals(0.0, distance(vectorOf(1.0, 0.0, 0.0), vectorOf(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(2.0), distance(vectorOf(1.0, 0.0, 0.0), vectorOf(1.0, 1.0, 1.0)))
            assertEquals(3.1622778182822584, distance(vectorOf(0.0, 1.0, 0.0), vectorOf(1.0, -2.0, 0.001)))
            assertEquals(0.0, distance(vectorOf(1.0, -2.0, 0.001), vectorOf(1.0, -2.0, 0.001)))
            assertEquals(9.695050335093676, distance(vectorOf(1.0, 2.0, 3.0), vectorOf(7.0, -5.0, 0.001)))
        }
    }

    @Test
    fun norm() {
        (EuclideanSpace(D3, RealField)) {
            assertEquals(0.0, norm(zero))
            assertEquals(1.0, norm(vectorOf(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(3.0), norm(vectorOf(1.0, 1.0, 1.0)))
            assertEquals(kotlin.math.sqrt(5.000001), norm(vectorOf(1.0, -2.0, 0.001)))
        }
    }

    @Test
    fun dotProduct() {
        (EuclideanSpace(D3, RealField)) {
            assertEquals(0.0, zero dot zero)
            assertEquals(0.0, zero dot vectorOf(1.0, 0.0, 0.0))
            assertEquals(0.0, vectorOf(1.0, -2.0, 0.001) dot zero)

            assertEquals(1.0, vectorOf(1.0, 0.0, 0.0) dot vectorOf(1.0, 0.0, 0.0))
            assertEquals(1.0, vectorOf(1.0, 0.0, 0.0) dot vectorOf(1.0, 1.0, 1.0))
            assertEquals(-2.0, vectorOf(0.0, 1.0, 0.0) dot vectorOf(1.0, -2.0, 0.001))
            assertEquals(3.0, vectorOf(1.0, 1.0, 1.0) dot vectorOf(1.0, 1.0, 1.0))
            assertEquals(5.000001, vectorOf(1.0, -2.0, 0.001) dot vectorOf(1.0, -2.0, 0.001))

            assertEquals(-2.997, vectorOf(1.0, 2.0, 3.0) dot vectorOf(7.0, -5.0, 0.001))
        }
    }

    @Test
    fun add() {
        (EuclideanSpace(D3, RealField)) {
            assertVectorEquals(
                    vectorOf(1.0, -2.0, 0.001),
                    vectorOf(1.0, -2.0, 0.001) + zero
            )
            assertVectorEquals(
                    vectorOf(8.0, -3.0, 3.001),
                    vectorOf(1.0, 2.0, 3.0) + vectorOf(7.0, -5.0, 0.001)
            )
        }
    }

    @Test
    fun multiply() {
        (EuclideanSpace(D3, RealField)) {
            assertVectorEquals(vectorOf(2.0, -4.0, 0.0), multiply(vectorOf(1.0, -2.0, 0.0), 2))
        }
    }

    @Test
    fun getZero() {
        assertVectorEquals(vectorOf(0.0), EuclideanSpace(D1, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0), EuclideanSpace(D2, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0), EuclideanSpace(D3, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0, 0.0, 0.0), EuclideanSpace(D5, RealField).zero)
    }

    @Test
    fun getDim() {
        assertEquals(D0, EuclideanSpace(D0, RealField).dim)
        assertEquals(D1, EuclideanSpace(D1, RealField).dim)
        assertEquals(D2, EuclideanSpace(D2, RealField).dim)
        assertEquals(D3, EuclideanSpace(D3, RealField).dim)
        assertEquals(D8, EuclideanSpace(D8, RealField).dim)
    }

    @Test
    fun getField() {
        assertEquals(RealField, EuclideanSpace(D3, RealField).field)
        assertEquals(FloatField, EuclideanSpace(D3, FloatField).field)
    }
}
