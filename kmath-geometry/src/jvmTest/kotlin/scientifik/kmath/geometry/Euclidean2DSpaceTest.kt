package scientifik.kmath.geometry

import kotlin.test.assertEquals
import kotlin.test.Test
import scientifik.kmath.dimensions.D2
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.invoke

internal class Euclidean2DSpaceTest {
    @Test
    fun getDim() {
        assertEquals(D2, Euclidean2DSpace.dim)
    }

    @Test
    fun getField() {
        assertEquals(RealField, Euclidean2DSpace.field)
    }

    @Test
    fun getZero() {
        assertVectorEquals(Vector2D(0.0, 0.0), Euclidean2DSpace.zero)
    }

    @Test
    fun norm() {
        Euclidean2DSpace {
            assertEquals(0.0, norm(zero))
            assertEquals(1.0, norm(Vector2D(1.0, 0.0)))
            assertEquals(kotlin.math.sqrt(2.0), norm(Vector2D(1.0, 1.0)))
            assertEquals(kotlin.math.sqrt(5.002001), norm(Vector2D(-2.0, 1.001)))
        }
    }

    @Test
    fun dotProduct() {
        Euclidean2DSpace {
            assertEquals(0.0, zero dot zero)
            assertEquals(0.0, zero dot Vector2D(1.0, 0.0))
            assertEquals(0.0, Vector2D(-2.0, 0.001) dot zero)
            assertEquals(0.0, Vector2D(1.0, 0.0) dot Vector2D(0.0, 1.0))

            assertEquals(1.0, Vector2D(1.0, 0.0) dot Vector2D(1.0, 0.0))
            assertEquals(-2.0, Vector2D(0.0, 1.0) dot Vector2D(1.0, -2.0))
            assertEquals(2.0, Vector2D(1.0, 1.0) dot Vector2D(1.0, 1.0))
            assertEquals(4.001001, Vector2D(-2.0, 1.001) dot Vector2D(-2.0, 0.001))

            assertEquals(-4.998, Vector2D(1.0, 2.0) dot Vector2D(-5.0, 0.001))
        }
    }

    @Test
    fun add() {
        Euclidean2DSpace {
            assertVectorEquals(
                    Vector2D(-2.0, 0.001),
                    Vector2D(-2.0, 0.001) + zero
            )
            assertVectorEquals(
                    Vector2D(-3.0, 3.001),
                    Vector2D(2.0, 3.0) + Vector2D(-5.0, 0.001)
            )
        }
    }

    @Test
    fun multiply() {
        Euclidean2DSpace {
            assertVectorEquals(Vector2D(-4.0, 0.0), multiply(Vector2D(-2.0, 0.0), 2))
            assertVectorEquals(Vector2D(4.0, 0.0), multiply(Vector2D(-2.0, 0.0), -2))
            assertVectorEquals(Vector2D(300.0, 0.0003), multiply(Vector2D(100.0, 0.0001), 3))
        }
    }
}
