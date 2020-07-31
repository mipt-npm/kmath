package scientifik.kmath.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import scientifik.kmath.dimensions.D3
import scientifik.kmath.operations.RealField

internal class Euclidean3DSpaceTest {
    @Test
    fun getDim() {
        assertEquals(D3, Euclidean3DSpace.dim)
    }

    @Test
    fun getField() {
        assertEquals(RealField, Euclidean3DSpace.field)
    }

    @Test
    fun getZero() {
        assertVectorEquals(Vector3D(0.0, 0.0, 0.0), Euclidean3DSpace.zero)
    }

    @Test
    fun distance() {
        with(Euclidean3DSpace) {
            assertEquals(0.0, distance(zero, zero))
            assertEquals(1.0, distance(zero, Vector3D(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(5.000001), distance(Vector3D(1.0, -2.0, 0.001), zero))
            assertEquals(0.0, distance(Vector3D(1.0, -2.0, 0.001), Vector3D(1.0, -2.0, 0.001)))
            assertEquals(0.0, distance(Vector3D(1.0, 0.0, 0.0), Vector3D(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(2.0), distance(Vector3D(1.0, 0.0, 0.0), Vector3D(1.0, 1.0, 1.0)))
            assertEquals(3.1622778182822584, distance(Vector3D(0.0, 1.0, 0.0), Vector3D(1.0, -2.0, 0.001)))
            assertEquals(0.0, distance(Vector3D(1.0, -2.0, 0.001), Vector3D(1.0, -2.0, 0.001)))
            assertEquals(9.695050335093676, distance(Vector3D(1.0, 2.0, 3.0), Vector3D(7.0, -5.0, 0.001)))
        }
    }

    @Test
    fun norm() {
        with(Euclidean3DSpace) {
            assertEquals(0.0, norm(zero))
            assertEquals(1.0, norm(Vector3D(1.0, 0.0, 0.0)))
            assertEquals(kotlin.math.sqrt(3.0), norm(Vector3D(1.0, 1.0, 1.0)))
            assertEquals(kotlin.math.sqrt(5.000001), norm(Vector3D(1.0, -2.0, 0.001)))
        }
    }

    @Test
    fun dotProduct() {
        with(Euclidean3DSpace) {
            assertEquals(0.0, zero dot zero)
            assertEquals(0.0, zero dot Vector3D(1.0, 0.0, 0.0))
            assertEquals(0.0, Vector3D(1.0, -2.0, 0.001) dot zero)

            assertEquals(1.0, Vector3D(1.0, 0.0, 0.0) dot Vector3D(1.0, 0.0, 0.0))
            assertEquals(1.0, Vector3D(1.0, 0.0, 0.0) dot Vector3D(1.0, 1.0, 1.0))
            assertEquals(-2.0, Vector3D(0.0, 1.0, 0.0) dot Vector3D(1.0, -2.0, 0.001))
            assertEquals(3.0, Vector3D(1.0, 1.0, 1.0) dot Vector3D(1.0, 1.0, 1.0))
            assertEquals(5.000001, Vector3D(1.0, -2.0, 0.001) dot Vector3D(1.0, -2.0, 0.001))

            assertEquals(-2.997, Vector3D(1.0, 2.0, 3.0) dot Vector3D(7.0, -5.0, 0.001))
        }
    }

    @Test
    fun add() {
        with(Euclidean3DSpace) {
            assertVectorEquals(
                    Vector3D(1.0, -2.0, 0.001),
                    Vector3D(1.0, -2.0, 0.001) + zero
            )
            assertVectorEquals(
                    Vector3D(8.0, -3.0, 3.001),
                    Vector3D(1.0, 2.0, 3.0) + Vector3D(7.0, -5.0, 0.001)
            )
        }
    }

    @Test
    fun multiply() {
        with(Euclidean3DSpace) {
            assertVectorEquals(Vector3D(2.0, -4.0, 0.0), multiply(Vector3D(1.0, -2.0, 0.0), 2))
        }
    }
}
