package scientifik.kmath.geometry.experimental.transformations

import org.junit.jupiter.api.Test
import scientifik.kmath.geometry.experimental.assertVectorEquals
import scientifik.kmath.geometry.experimental.grid
import scientifik.kmath.geometry.experimental.real2DVectorSpace
import scientifik.kmath.geometry.experimental.vectorOf

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
    fun projectIntoPlane() {
        TODO()
    }
}