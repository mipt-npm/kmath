package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.*
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.invoke

internal class RealEuclideanVectorSpaceTest {
    @Test
    fun distance() {
        Real3DVectorSpace {
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
        Real3DVectorSpace {
            assertEquals(0.0, norm(zero))
            assertEquals(1.0, norm(vectorOf(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(3.0), norm(vectorOf(1.0, 1.0, 1.0)))
            assertEquals(kotlin.math.sqrt(5.000001), norm(vectorOf(1.0, -2.0, 0.001)))
        }
    }

    @Test
    fun dotProduct() {
        Real3DVectorSpace {
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
        Real3DVectorSpace {
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
        Real3DVectorSpace {
            assertVectorEquals(vectorOf(2.0, -4.0, 0.0), multiply(vectorOf(1.0, -2.0, 0.0), 2))
        }
    }

    @Test
    fun getZero() {
        assertVectorEquals(vectorOf(0.0), RealEuclideanVectorSpace(D1).zero)
        assertVectorEquals(vectorOf(0.0, 0.0), RealEuclideanVectorSpace(D2).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0), RealEuclideanVectorSpace(D3).zero)
        assertVectorEquals(vectorOf(0.0, 0.0, 0.0, 0.0, 0.0), RealEuclideanVectorSpace(D5).zero)
    }

    @Test
    fun getDim() {
        assertEquals(D0, RealEuclideanVectorSpace(D0).dim)
        assertEquals(D1, RealEuclideanVectorSpace(D1).dim)
        assertEquals(D2, RealEuclideanVectorSpace(D2).dim)
        assertEquals(D3, RealEuclideanVectorSpace(D3).dim)
        assertEquals(D8, RealEuclideanVectorSpace(D8).dim)
    }

    @Test
    fun getField() {
        assertEquals(RealField, RealEuclideanVectorSpace(D0).field)
        assertEquals(RealField, RealEuclideanVectorSpace(D1).field)
        assertEquals(RealField, RealEuclideanVectorSpace(D2).field)
        assertEquals(RealField, RealEuclideanVectorSpace(D3).field)
        assertEquals(RealField, RealEuclideanVectorSpace(D8).field)
    }
}
