package scientifik.kmath.geometry.transformations

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.geometry.InnerProductSpace
import scientifik.kmath.geometry.Vector

class ShiftTransformation<T, D : Dimension, V : Vector<T, D>>(
        private val vectorSpace: InnerProductSpace<T, D, V>,
        private val bias: V
) : Transformation<T, D, V> {
    override fun invoke(v: V): V = vectorSpace.add(v, bias)
    override val input: InnerProductSpace<T, D, V> get() = vectorSpace
    override val output: InnerProductSpace<T, D, V> get() = vectorSpace
}

fun <T, D : Dimension, V : Vector<T, D>> InnerProductSpace<T, D, V>.shiftBy(bias: V): Transformation<T, D, V> =
        ShiftTransformation(this, bias)

fun <T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> Relation<T1, D1, V1, T2, D2, V2>.shiftBy(
        bias: V2
): Relation<T1, D1, V1, T2, D2, V2> = this.composeWith(ShiftTransformation(this.output, bias))