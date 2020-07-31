package scientifik.kmath.geometry.transformations

import org.junit.jupiter.api.Test
import scientifik.kmath.geometry.*
import scientifik.kmath.operations.invoke
import kotlin.math.sqrt

internal class LinearMapTest {
    @Test
    fun matrixTransformationRotationCounterClockwise90degree() {
        Real2DVectorSpace {
            val transformation = mapBy(vectorOf(
                    vectorOf(0.0, -1.0),
                    vectorOf(1.0, 0.0)
            ))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                assertVectorEquals(vectorOf(-y, x), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun matrixTransformationRotationClockwise45degree() {
        Real2DVectorSpace {
            val a = 1.0 / sqrt(2.0)
            val transformation = mapBy(vectorOf(
                    vectorOf(a, a),
                    vectorOf(-a, a)
            ))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                assertVectorEquals(vectorOf(a * x + a * y, -a * x + a * y), transformation(vectorOf(x, y)))
            }
        }
    }

    @Test
    fun matrixRelation() {
        Real3DVectorSpace {
            val relation = mapBy(Real2DVectorSpace, vectorOf(
                    vectorOf(6.0, -2.0, 100.001),
                    vectorOf(-1.0, 0.0, 3.0)
            ))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                assertVectorEquals(vectorOf(
                        6.0 * x - 2.0 * y + 100.001 * 7.0,
                        -1.0 * x + 3.0 * 7.0
                ), relation(vectorOf(x, y, 7.0)))
            }
        }
    }
}
