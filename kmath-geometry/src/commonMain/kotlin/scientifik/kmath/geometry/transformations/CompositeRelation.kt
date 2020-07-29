package scientifik.kmath.geometry.transformations

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.geometry.InnerProductSpace
import scientifik.kmath.geometry.Vector

data class CompositeRelation<
        TIn, DIn : Dimension, VIn : Vector<TIn, DIn>,
        TInner, DInner : Dimension, VInner : Vector<TInner, DInner>,
        TOut, DOut : Dimension, VOut : Vector<TOut, DOut>
        >(
        private val first: Relation<TIn, DIn, VIn, TInner, DInner, VInner>,
        private val second: Relation<TInner, DInner, VInner, TOut, DOut, VOut>
) : Relation<TIn, DIn, VIn, TOut, DOut, VOut> {
    override fun invoke(v: VIn): VOut = second.invoke(first.invoke(v))
    override val input: InnerProductSpace<TIn, DIn, VIn> get() = first.input
    override val output: InnerProductSpace<TOut, DOut, VOut> get() = second.output
}

fun <TIn, DIn : Dimension, VIn : Vector<TIn, DIn>,
        TInner, DInner : Dimension, VInner : Vector<TInner, DInner>,
        TOut, DOut : Dimension, VOut : Vector<TOut, DOut>
        > Relation<TIn, DIn, VIn, TInner, DInner, VInner>.composeWith(
        relation: Relation<TInner, DInner, VInner, TOut, DOut, VOut>
): Relation<TIn, DIn, VIn, TOut, DOut, VOut> = CompositeRelation(this, relation)
