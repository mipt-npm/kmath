package scientifik.kmath.geometry

import scientifik.kmath.dimensions.D3
import scientifik.kmath.linear.Point
import scientifik.kmath.operations.ExtendedField
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.SpaceElement
import kotlin.math.sqrt

data class Vector3D(
        val x: Double,
        val y: Double,
        val z: Double
) : Point<Double>, Vector<Double, D3>, SpaceElement<Vector3D, Vector3D, Euclidean3DSpace> {
    override val size: Int get() = 3

    override fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> error("Accessing outside of point bounds")
    }

    override fun iterator(): Iterator<Double> = listOf(x, y, z).iterator()

    override val context: Euclidean3DSpace get() = Euclidean3DSpace

    override fun unwrap(): Vector3D = this

    override fun Vector3D.wrap(): Vector3D = this
}

object Euclidean3DSpace : InnerProductSpace<Double, D3, Vector3D> {
    override val dim: D3 get() = D3
    override val field: ExtendedField<Double> get() = RealField
    override val zero: Vector3D = Vector3D(0.0, 0.0, 0.0)

    override fun norm(a: Vector3D): Double = with(a) { sqrt(x * x + y * y + z * z) }

    override fun dotProduct(a: Vector3D, b: Vector3D): Double =
            a.x * b.x + a.y * b.y + a.z * b.z

    override fun add(a: Vector3D, b: Vector3D): Vector3D =
            Vector3D(a.x + b.x, a.y + b.y, a.z + b.z)

    override fun multiply(a: Vector3D, k: Double): Vector3D =
            Vector3D(a.x * k, a.y * k, a.z * k)

    override fun multiply(a: Vector3D, k: Number): Vector3D =
            multiply(a, k.toDouble())

    override fun vectorFrom(x: Sequence<Double>): Vector3D {
        val iterator = x.iterator()
        val result = Vector3D(iterator.next(), iterator.next(), iterator.next())
        require(!iterator.hasNext())
        return result
    }
}
