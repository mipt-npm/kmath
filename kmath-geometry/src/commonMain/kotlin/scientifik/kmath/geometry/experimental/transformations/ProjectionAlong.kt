package scientifik.kmath.geometry.experimental.transformations

import scientifik.kmath.geometry.experimental.Dimension
import scientifik.kmath.geometry.experimental.Vector
import scientifik.kmath.geometry.experimental.VectorSpace

// TODO normalize normal right away?
class ProjectionAlong<T, D : Dimension, V : Vector<T, D>>(
        private val vectorSpace: VectorSpace<T, D, V>,
        private val normal: V,
        private val base: V
) : Transformation<T, D, V> {
    override fun invoke(v: V): V = with(vectorSpace) {
        v + normal * field.divide((base - v) dot normal, normal dot normal)
    }

    override val input: VectorSpace<T, D, V> get() = vectorSpace
    override val output: VectorSpace<T, D, V> get() = vectorSpace
}

fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.projection(normal: V, base: V): Transformation<T, D, V> =
        ProjectionAlong(this, normal, base)

fun <T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> Relation<T1, D1, V1, T2, D2, V2>.projection(
        normal: V2,
        base: V2
): Relation<T1, D1, V1, T2, D2, V2> = this.composeWith(ProjectionAlong(this.output, normal, base))