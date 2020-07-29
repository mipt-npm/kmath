package scientifik.kmath.geometry.transformations

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.geometry.Vector
import scientifik.kmath.geometry.VectorSpace
import scientifik.kmath.structures.asSequence

class LinearMap<T, D1 : Dimension, V1 : Vector<T, D1>, D2 : Dimension, V2 : Vector<T, D2>>(
        override val input: VectorSpace<T, D1, V1>,
        override val output: VectorSpace<T, D2, V2>,
        private val matrix: Vector<V1, D2>
) : Relation<T, D1, V1, T, D2, V2> {
    override fun invoke(v: V1): V2 =
            output.vectorFrom(matrix.asSequence().map { row -> input.dotProduct(row, v) })
}

fun <T, D1 : Dimension, V1 : Vector<T, D1>, D2 : Dimension, V2 : Vector<T, D2>> VectorSpace<T, D1, V1>.mapBy(
        toVectorSpace: VectorSpace<T, D2, V2>,
        matrix: Vector<V1, D2>
): Relation<T, D1, V1, T, D2, V2> = LinearMap(this, toVectorSpace, matrix)

fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.mapBy(
        matrix: Vector<V, D>
): Transformation<T, D, V> = LinearMap(this, this, matrix)