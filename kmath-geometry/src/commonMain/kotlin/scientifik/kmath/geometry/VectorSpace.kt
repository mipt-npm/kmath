package scientifik.kmath.geometry

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.linear.Point
import scientifik.kmath.operations.ExtendedField
import scientifik.kmath.operations.Space

interface Vector<T, D : Dimension> : Point<T>

interface VectorSpace<T, D : Dimension, V : Vector<T, D>> : Space<V> {
    fun vectorFrom(x: Sequence<T>): V

    fun multiply(a: V, k: T): V

    operator fun V.times(k: T) = multiply(this, k)
    operator fun T.times(v: V) = multiply(v, this)

    val dim: D
    val field: ExtendedField<T>
}

interface MetricSpace<T, D : Dimension, V : Vector<T, D>> : VectorSpace<T, D, V> {
    /**
     * L2 distance
     */
    fun distance(a: V, b: V): T

    fun V.distanceTo(other: V): T = distance(this, other)
}

interface NormedVectorSpace<T, D : Dimension, V : Vector<T, D>> : MetricSpace<T, D, V> {
    fun norm(a: V): T

    override fun distance(a: V, b: V): T = norm(a - b)
}

interface InnerProductSpace<T, D : Dimension, V : Vector<T, D>> : NormedVectorSpace<T, D, V> {
    /**
     * Scalar/Inner product of 2 vectors
     */
    fun dotProduct(a: V, b: V): T

    override fun norm(a: V): T = field.sqrt(dotProduct(a, a))

    infix fun V.dot(other: V): T = dotProduct(this, other)
}
