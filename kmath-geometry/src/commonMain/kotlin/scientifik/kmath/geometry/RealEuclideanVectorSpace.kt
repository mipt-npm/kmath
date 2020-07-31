package scientifik.kmath.geometry

import scientifik.kmath.dimensions.*
import scientifik.kmath.operations.ExtendedField
import scientifik.kmath.operations.RealField
import kotlin.math.sqrt

data class RealVector<D : Dimension>(private val array: DoubleArray) : Vector<Double, D> {
    override val size: Int get() = array.size

    override fun get(index: Int): Double = when {
        index < 0 || index >= size -> error("Accessing outside of point bounds")
        else -> array[index]
    }

    override fun iterator(): Iterator<Double> = array.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RealVector<*>

        return array.contentEquals(other.array)
    }

    override fun hashCode(): Int = array.contentHashCode()

    override fun toString(): String = "RealVector(${array.contentToString()})"
}

fun vectorOf(a1: Double): RealVector<D1> = RealVector(doubleArrayOf(a1))
fun vectorOf(a1: Double, a2: Double): RealVector<D2> = RealVector(doubleArrayOf(a1, a2))
fun vectorOf(a1: Double, a2: Double, a3: Double): RealVector<D3> = RealVector(doubleArrayOf(a1, a2, a3))
fun vectorOf(a1: Double, a2: Double, a3: Double, a4: Double): RealVector<D4> = RealVector(doubleArrayOf(a1, a2, a3, a4))
fun vectorOf(a1: Double, a2: Double, a3: Double, a4: Double, a5: Double): RealVector<D5> = RealVector(doubleArrayOf(a1, a2, a3, a4, a5))

// TODO optimize by removing fold?
class RealEuclideanVectorSpace<D : Dimension>(override val dim: D) : InnerProductSpace<Double, D, RealVector<D>> {
    private val dimSize = dim.dim.toInt()
    private val range = 0 until dimSize

    override val field: ExtendedField<Double> get() = RealField

    override fun distance(a: RealVector<D>, b: RealVector<D>): Double =
            sqrt(range.fold(0.0) { acc, index -> val d = a[index] - b[index]; acc + d * d })

    override fun norm(a: RealVector<D>): Double =
            sqrt(range.fold(0.0) { acc, index -> acc + a[index] * a[index] })

    override fun dotProduct(a: RealVector<D>, b: RealVector<D>): Double =
            range.fold(0.0) { acc, index -> acc + a[index] * b[index] }

    override fun add(a: RealVector<D>, b: RealVector<D>): RealVector<D> =
            RealVector(DoubleArray(dimSize) { index -> a[index] + b[index] })

    override fun multiply(a: RealVector<D>, k: Number): RealVector<D> =
            RealVector(DoubleArray(dimSize) { index -> a[index] * k.toDouble() })

    override val zero: RealVector<D> = RealVector(DoubleArray(dimSize))

    override fun vectorFrom(x: Sequence<Double>): RealVector<D> {
        val array = x.toList().toDoubleArray()
        require(array.size == dimSize)
        return RealVector(array)
    }

}

val Real1DVectorSpace = RealEuclideanVectorSpace(D1)
val Real2DVectorSpace = RealEuclideanVectorSpace(D2)
val Real3DVectorSpace = RealEuclideanVectorSpace(D3)
val Real4DVectorSpace = RealEuclideanVectorSpace(D4)
val Real5DVectorSpace = RealEuclideanVectorSpace(D5)
