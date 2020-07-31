package scientifik.kmath.geometry.transformations

import org.junit.jupiter.api.Test
import scientifik.kmath.geometry.assertVectorEquals
import scientifik.kmath.geometry.grid
import scientifik.kmath.geometry.real2DVectorSpace
import scientifik.kmath.geometry.vectorOf

internal class ProjectionAlongTest {
    @Test
    fun projectionIntoYEqualsX() {
        with(real2DVectorSpace) {
            val transformation = projection(vectorOf(-2.0, 2.0), vectorOf(2.3, 2.3))

            assertVectorEquals(zero, transformation(zero))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val d = (y - x) / 2.0
                assertVectorEquals(vectorOf(x + d, y - d), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectionOntoLine() {
        with(real2DVectorSpace) {
            val a = 5.0
            val b = -3.0
            val c = -15.0
            val transformation = projection(normal = vectorOf(-5.0, 3.0), base = vectorOf(3.0, 0.0))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val xProj = (b * (b * x - a * y) - a * c) / (a * a + b * b)
                val yProj = (a * (-b * x + a * y) - b * c) / (a * a + b * b)
                assertVectorEquals(vectorOf(xProj, yProj), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectOntoPlane() {
        TODO()
    }
}
