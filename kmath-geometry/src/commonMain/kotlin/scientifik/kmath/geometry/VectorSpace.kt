package scientifik.kmath.geometry

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.linear.Point
import scientifik.kmath.operations.Field
import scientifik.kmath.operations.Space

interface Vector<T, D : Dimension> : Point<T>

interface VectorSpace<T, D : Dimension, V : Vector<T, D>> : Space<V> {
    /**
     * L2 distance
     */
    fun distance(a: V, b: V): T = norm(a - b)

    fun norm(a: V): T

    /**
     * Scalar product
     */
    fun dotProduct(a: V, b: V): T

    fun vectorFrom(x: Sequence<T>): V

    // TODO what about it?
    fun multiply(a: V, k: T): V = multiply(a, k as Number)

    operator fun V.times(k: T) = multiply(this, k)
    operator fun T.times(v: V) = multiply(v, this)

    fun V.distanceTo(other: V): T = distance(this, other)
    infix fun V.dot(other: V): T = dotProduct(this, other)

    val dim: D
    val field: Field<T>
}