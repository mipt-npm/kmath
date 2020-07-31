package scientifik.kmath.geometry.transformations

import org.junit.jupiter.api.Test
import scientifik.kmath.geometry.*
import scientifik.kmath.operations.invoke

internal class ShiftTransformationTest {
    @Test
    fun shiftBy0() {
        Real2DVectorSpace {
            val transformation = shiftBy(zero)

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val vector = vectorOf(x, y)
                assertVectorEquals(vector, transformation(vector))
            }
        }

        Real3DVectorSpace {
            val transformation = shiftBy(zero)

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (x, y) ->
                val vector = vectorOf(x, y, 7.0)
                assertVectorEquals(vector, transformation(vector))
            }
        }
    }

    @Test
    fun shift() {
        Real4DVectorSpace {
            val transformation = shiftBy(vectorOf(1.0, -2.0, 0.01, 111.0))

            grid(-10.0..10.0, -10.0..10.0, 0.15).forEach { (a, b) ->
                val vector = vectorOf(a, b, b, a)
                assertVectorEquals(vectorOf(a + 1.0, b - 2.0, b + 0.01, a + 111.0), transformation(vector))
            }
        }
    }
}
