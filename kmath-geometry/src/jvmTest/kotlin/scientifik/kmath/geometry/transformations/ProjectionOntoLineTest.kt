package scientifik.kmath.geometry.transformations

import org.junit.jupiter.api.Test
import scientifik.kmath.geometry.*
import scientifik.kmath.operations.invoke

internal class ProjectionOntoLineTest {
    @Test
    fun projectionIntoOx() {
        real2DVectorSpace {
            val projectionToOx = projection(Line(zero, vectorOf(1.0, 0.0)))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                assertVectorEquals(vectorOf(x, 0.0), projectionToOx(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectionIntoOy() {
        real2DVectorSpace {
            val transformation = projection(Line(zero, vectorOf(0.0, 1.0)))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                assertVectorEquals(vectorOf(0.0, y), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectionIntoYEqualsX() {
        real2DVectorSpace {
            val transformation = projection(Line(zero, vectorOf(1.0, 1.0)))

            assertVectorEquals(zero, transformation(zero))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val d = (y - x) / 2.0
                assertVectorEquals(vectorOf(x + d, y - d), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectionOntoLine2d() {
        real2DVectorSpace {
            val a = 5.0
            val b = -3.0
            val c = -15.0
            val transformation = projection(Line(vectorOf(3.0, 0.0), vectorOf(3.0, 5.0)))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val xProj = (b * (b * x - a * y) - a * c) / (a * a + b * b)
                val yProj = (a * (-b * x + a * y) - b * c) / (a * a + b * b)
                assertVectorEquals(vectorOf(xProj, yProj), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun projectionOntoLine3d() {
        TODO()
    }
}
