package scientifik.kmath.geometry.experimental

import scientifik.kmath.linear.Point
import scientifik.kmath.operations.Field
import scientifik.kmath.operations.Space
import scientifik.kmath.structures.asSequence

interface Vector<T, D : Dimension> : Point<T>

typealias Vector0D<T> = Vector<T, D0>
typealias Vector1D<T> = Vector<T, D1>
typealias Vector2D<T> = Vector<T, D2>
typealias Vector3D<T> = Vector<T, D3>
typealias Vector4D<T> = Vector<T, D4>
typealias Vector5D<T> = Vector<T, D5>
typealias Vector6D<T> = Vector<T, D6>
typealias Vector7D<T> = Vector<T, D7>
typealias Vector8D<T> = Vector<T, D8>
typealias Vector9D<T> = Vector<T, D9>

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

    // TODO do we really need it? With that we can create vectors outside of the space and bring it here or convert
    // TODO optimize with `if (v is V) v else ...`
    fun cast(v: Vector<T, D>): V = vectorFrom(v.asSequence())

    // TODO remove?
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

typealias VectorSpace0D<T> = VectorSpace<T, D0, Vector0D<T>>
typealias VectorSpace1D<T> = VectorSpace<T, D1, Vector1D<T>>
typealias VectorSpace2D<T> = VectorSpace<T, D2, Vector2D<T>>
typealias VectorSpace3D<T> = VectorSpace<T, D3, Vector3D<T>>
typealias VectorSpace4D<T> = VectorSpace<T, D4, Vector4D<T>>
typealias VectorSpace5D<T> = VectorSpace<T, D5, Vector5D<T>>
typealias VectorSpace6D<T> = VectorSpace<T, D6, Vector6D<T>>
typealias VectorSpace7D<T> = VectorSpace<T, D7, Vector7D<T>>
typealias VectorSpace8D<T> = VectorSpace<T, D8, Vector8D<T>>
typealias VectorSpace9D<T> = VectorSpace<T, D9, Vector9D<T>>

//fun <T, V : Vector<T, D1>> VectorSpace<T, D1, V>.vectorOf(a1: T): V = vectorFrom(sequenceOf(a1))
//fun <T, V : Vector<T, D2>> VectorSpace<T, D2, V>.vectorOf(a1: T, a2: T): V = vectorFrom(sequenceOf(a1, a2))
//fun <T, V : Vector<T, D3>> VectorSpace<T, D3, V>.vectorOf(a1: T, a2: T, a3: T): V = vectorFrom(sequenceOf(a1, a2, a3))
//fun <T, V : Vector<T, D4>> VectorSpace<T, D4, V>.vectorOf(a1: T, a2: T, a3: T, a4: T): V = vectorFrom(sequenceOf(a1, a2, a3, a4))
//fun <T, V : Vector<T, D5>> VectorSpace<T, D5, V>.vectorOf(a1: T, a2: T, a3: T, a4: T, a5: T): V = vectorFrom(sequenceOf(a1, a2, a3, a4, a5))
//fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.vectorFrom(list: List<T>): V = vectorFrom(list.asSequence())
//fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.vectorFrom(vararg x: T): V = vectorFrom(x.asSequence())

// TODO add component1(), component2(), ...