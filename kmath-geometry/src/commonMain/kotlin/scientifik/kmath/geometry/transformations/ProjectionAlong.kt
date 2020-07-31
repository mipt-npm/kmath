package scientifik.kmath.geometry.transformations

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.geometry.InnerProductSpace
import scientifik.kmath.geometry.Vector
import scientifik.kmath.operations.invoke

// TODO normalize normal right away?
class ProjectionAlong<T, D : Dimension, V : Vector<T, D>>(
        private val vectorSpace: InnerProductSpace<T, D, V>,
        private val normal: V,
        private val base: V
) : Transformation<T, D, V> {
    override fun invoke(v: V): V = vectorSpace {
        v + normal * field.divide((base - v) dot normal, normal dot normal)
    }

    override val input: InnerProductSpace<T, D, V> get() = vectorSpace
    override val output: InnerProductSpace<T, D, V> get() = vectorSpace
}

fun <T, D : Dimension, V : Vector<T, D>> InnerProductSpace<T, D, V>.projection(normal: V, base: V): Transformation<T, D, V> =
        ProjectionAlong(this, normal, base)

fun <T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> Relation<T1, D1, V1, T2, D2, V2>.projection(
        normal: V2,
        base: V2
): Relation<T1, D1, V1, T2, D2, V2> = this.composeWith(ProjectionAlong(this.output, normal, base))
