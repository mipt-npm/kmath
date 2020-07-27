package scientifik.kmath.geometry.experimental.transformations

import scientifik.kmath.geometry.experimental.Dimension
import scientifik.kmath.geometry.experimental.Vector
import scientifik.kmath.geometry.experimental.VectorSpace

class MapTransformation<T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>>(
        override val input: VectorSpace<T1, D1, V1>,
        override val output: VectorSpace<T2, D2, V2>,
        private val map: (V1) -> V2
) : Relation<T1, D1, V1, T2, D2, V2> {
    override fun invoke(v: V1): V2 = map(v)
}

fun <T, D1 : Dimension, V1 : Vector<T, D1>, D2 : Dimension, V2 : Vector<T, D2>> VectorSpace<T, D1, V1>.mapTo(
        toVectorSpace: VectorSpace<T, D2, V2>,
        map: (V1) -> V2
): Relation<T, D1, V1, T, D2, V2> = MapTransformation(this, toVectorSpace, map)

fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.map(
        map: (V) -> V
): Transformation<T, D, V> = MapTransformation(this, this, map)


fun <TIn, DIn : Dimension, VIn : Vector<TIn, DIn>,
        TInner, DInner : Dimension, VInner : Vector<TInner, DInner>,
        TOut, DOut : Dimension, VOut : Vector<TOut, DOut>
        > Relation<TIn, DIn, VIn, TInner, DInner, VInner>.mapTo(
        toVectorSpace: VectorSpace<TOut, DOut, VOut>,
        map: (VInner) -> VOut
): Relation<TIn, DIn, VIn, TOut, DOut, VOut> = this.composeWith(MapTransformation(this.output, toVectorSpace, map))

fun <T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> Relation<T1, D1, V1, T2, D2, V2>.map(
        map: (V2) -> V2
): Relation<T1, D1, V1, T2, D2, V2> = this.composeWith(MapTransformation(this.output, this.output, map))