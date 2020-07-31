package scientifik.kmath.geometry

import scientifik.kmath.dimensions.D2
import scientifik.kmath.linear.Point
import scientifik.kmath.operations.ExtendedField
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.SpaceElement
import kotlin.math.sqrt

data class Vector2D(
        val x: Double,
        val y: Double
) : Point<Double>, Vector<Double, D2>, SpaceElement<Vector2D, Vector2D, Euclidean2DSpace> {
    override val size: Int get() = 2

    override fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        else -> error("Accessing outside of point bounds")
    }

    override fun iterator(): Iterator<Double> = listOf(x, y).iterator()

    override val context: Euclidean2DSpace get() = Euclidean2DSpace

    override fun unwrap(): Vector2D = this

    override fun Vector2D.wrap(): Vector2D = this
}

/**
 * 2D Euclidean space
 */
object Euclidean2DSpace : InnerProductSpace<Double, D2, Vector2D> {
    override val dim: D2 get() = D2
    override val field: ExtendedField<Double> get() = RealField
    override val zero: Vector2D = Vector2D(0.0, 0.0)

    override fun norm(a: Vector2D): Double = with(a) { sqrt(x * x + y * y) }

    override fun dotProduct(a: Vector2D, b: Vector2D): Double =
            a.x * b.x + a.y * b.y

    override fun add(a: Vector2D, b: Vector2D): Vector2D =
            Vector2D(a.x + b.x, a.y + b.y)

    override fun multiply(a: Vector2D, k: Number): Vector2D =
            Vector2D(a.x * k.toDouble(), a.y * k.toDouble())

    override fun vectorFrom(x: Sequence<Double>): Vector2D {
        val iterator = x.iterator()
        val result = Vector2D(iterator.next(), iterator.next())
        require(!iterator.hasNext())
        return result
    }
}
