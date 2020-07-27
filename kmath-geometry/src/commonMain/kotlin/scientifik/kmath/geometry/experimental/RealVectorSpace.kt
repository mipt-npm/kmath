package scientifik.kmath.geometry.experimental

import scientifik.kmath.operations.Field
import scientifik.kmath.operations.RealField
import scientifik.kmath.structures.asSequence
import kotlin.math.sqrt

data class RealVector<D : Dimension>(private val array: DoubleArray) : Vector<Double, D> {
    override val size: Int = array.size

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

    override fun toString(): String = array.contentToString()
}

fun vectorOf(a1: Double): RealVector<D1> = RealVector(doubleArrayOf(a1))
fun vectorOf(a1: Double, a2: Double): RealVector<D2> = RealVector(doubleArrayOf(a1, a2))
fun vectorOf(a1: Double, a2: Double, a3: Double): RealVector<D3> = RealVector(doubleArrayOf(a1, a2, a3))
fun vectorOf(a1: Double, a2: Double, a3: Double, a4: Double): RealVector<D4> = RealVector(doubleArrayOf(a1, a2, a3, a4))
fun vectorOf(a1: Double, a2: Double, a3: Double, a4: Double, a5: Double): RealVector<D5> = RealVector(doubleArrayOf(a1, a2, a3, a4, a5))

// TODO optimize by removing fold?
class RealVectorSpace<D : Dimension>(override val dim: D) : VectorSpace<Double, D, RealVector<D>> {
    private val range = 0 until dim.size

    override val field: Field<Double> = RealField

    override fun distance(a: RealVector<D>, b: RealVector<D>): Double =
            sqrt(range.fold(0.0) { acc, index -> val d = a[index] - b[index]; acc + d * d })

    override fun norm(a: RealVector<D>): Double =
            sqrt(range.fold(0.0) { acc, index -> acc + a[index] * a[index] })

    override fun dotProduct(a: RealVector<D>, b: RealVector<D>): Double =
            range.fold(0.0) { acc, index -> acc + a[index] * b[index] }

    override fun add(a: RealVector<D>, b: RealVector<D>): RealVector<D> =
            RealVector(DoubleArray(dim.size) { index -> a[index] + b[index] })

    override fun multiply(a: RealVector<D>, k: Number): RealVector<D> =
            RealVector(DoubleArray(dim.size) { index -> a[index] * k.toDouble() })

    override val zero: RealVector<D> = RealVector(DoubleArray(dim.size))


    override fun vectorFrom(x: Sequence<Double>): RealVector<D> {
        val array = x.toList().toDoubleArray()
        require(array.size == dim.size)
        return RealVector(array)
    }

    // TODO optimize?
    override fun cast(v: Vector<Double, D>): RealVector<D> = if (v is RealVector<D>) v else {
        val array = v.asSequence().toList().toDoubleArray()
        require(array.size == dim.size)
        RealVector(array)
    }
}

val real1DVectorSpace = RealVectorSpace(D1)
val real2DVectorSpace = RealVectorSpace(D2)
val real3DVectorSpace = RealVectorSpace(D3)
val real4DVectorSpace = RealVectorSpace(D4)
val real5DVectorSpace = RealVectorSpace(D5)
