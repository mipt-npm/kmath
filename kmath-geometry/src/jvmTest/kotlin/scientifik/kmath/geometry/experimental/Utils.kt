package scientifik.kmath.geometry.experimental

import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun grid(xRange: ClosedRange<Double>,
         yRange: ClosedRange<Double>,
         step: Double): List<Pair<Double, Double>> {
    fun generateList(range: ClosedRange<Double>): List<Double> =
            generateSequence(range.start) { previous ->
                if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
                val next = previous + step
                if (next > range.endInclusive) null else next
            }.toList()

    val xs = generateList(xRange)
    val ys = generateList(yRange)

    return xs.flatMap { x -> ys.map { y -> x to y } }
}

fun <D : Dimension> assertVectorEquals(expected: Vector<Double, D>, actual: Vector<Double, D>, eps: Double = 1e-6) {
    assertEquals(expected.size, actual.size, "Unexpected vector size")
    for (i in 0 until expected.size) {
        val expectedValue = expected[i]
        val actualValue = actual[i]
        assertTrue(abs(expectedValue - actualValue) < eps, "Component of the vector at index $i '$actualValue' does equal to expected value '$expectedValue'")
    }
}