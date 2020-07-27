package scientifik.kmath.geometry.experimental.transformations

import scientifik.kmath.geometry.experimental.Dimension
import scientifik.kmath.geometry.experimental.Line
import scientifik.kmath.geometry.experimental.Vector
import scientifik.kmath.geometry.experimental.VectorSpace

class ProjectionIntoLine<T, D : Dimension, V : Vector<T, D>>(
        private val vectorSpace: VectorSpace<T, D, V>,
        private val base: V,
        private val direction: V
) : Transformation<T, D, V> {
    private val dDotD = vectorSpace.dotProduct(direction, direction)
    private val dDotB = vectorSpace.dotProduct(direction, base)

    // base + (direction dot (v - base)) / (direction dot direction) * direction
    override fun invoke(v: V): V = with(vectorSpace) {
        base + direction * with(field) {
            ((direction dot v) - dDotB) / dDotD
        }
    }

    override val input: VectorSpace<T, D, V> get() = vectorSpace
    override val output: VectorSpace<T, D, V> get() = vectorSpace
}

fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.projection(line: Line<T, D, V>): Transformation<T, D, V> =
        ProjectionIntoLine(this, line.base, line.direction)

fun <T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> Relation<T1, D1, V1, T2, D2, V2>.projection(
        line: Line<T2, D2, V2>
): Relation<T1, D1, V1, T2, D2, V2> = this.composeWith(ProjectionIntoLine(this.output, line.base, line.direction))
