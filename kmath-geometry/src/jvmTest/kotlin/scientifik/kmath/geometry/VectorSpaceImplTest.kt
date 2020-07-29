package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.*
import scientifik.kmath.geometry.D8
import scientifik.kmath.geometry.VectorSpaceImpl
import scientifik.kmath.geometry.assertVectorEquals
import scientifik.kmath.geometry.vectorOf
import scientifik.kmath.operations.ComplexField
import scientifik.kmath.operations.FloatField
import scientifik.kmath.operations.RealField

internal class VectorSpaceImplTest {
    @Test
    fun distance() {
        with(VectorSpaceImpl(D3, RealField)) {
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
        with(VectorSpaceImpl(D3, RealField)) {
            assertEquals(0.0, norm(zero))
            assertEquals(1.0, norm(vectorOf(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(3.0), norm(vectorOf(1.0, 1.0, 1.0)))
            assertEquals(kotlin.math.sqrt(5.000001), norm(vectorOf(1.0, -2.0, 0.001)))
        }
    }

    @Test
    fun dotProduct() {
        with(VectorSpaceImpl(D3, RealField)) {
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
        with(VectorSpaceImpl(D3, RealField)) {
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
        with(VectorSpaceImpl(D3, RealField)) {
            assertVectorEquals(vectorOf(2.0, -4.0, 0.0), multiply(vectorOf(1.0, -2.0, 0.0), 2))
        }
    }

    @Test
    fun getZero() {
        assertVectorEquals(vectorOf(0.0), VectorSpaceImpl(D1, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0), VectorSpaceImpl(D2, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0), VectorSpaceImpl(D3, RealField).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0, 0.0, 0.0), VectorSpaceImpl(D5, RealField).zero)
    }

    @Test
    fun getDim() {
        assertEquals(D0, VectorSpaceImpl(D0, RealField).dim)
        assertEquals(D1, VectorSpaceImpl(D1, RealField).dim)
        assertEquals(D2, VectorSpaceImpl(D2, RealField).dim)
        assertEquals(D3, VectorSpaceImpl(D3, RealField).dim)
        assertEquals(D8, VectorSpaceImpl(D8, RealField).dim)
    }

    @Test
    fun getField() {
        assertEquals(RealField, VectorSpaceImpl(D3, RealField).field)
        assertEquals(FloatField, VectorSpaceImpl(D3, FloatField, FloatField::sqrt).field)
        assertEquals(ComplexField, VectorSpaceImpl(D3, ComplexField, ComplexField::sqrt).field)
    }
}