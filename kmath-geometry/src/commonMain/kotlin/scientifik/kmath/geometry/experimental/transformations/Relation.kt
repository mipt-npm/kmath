package scientifik.kmath.geometry.experimental.transformations

import scientifik.kmath.geometry.experimental.Dimension
import scientifik.kmath.geometry.experimental.Vector
import scientifik.kmath.geometry.experimental.VectorSpace

interface Relation<T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> : (V1) -> V2 {
    override operator fun invoke(v: V1): V2

    // TODO do we need to add parameter for VS : VectorSpace<Tx, Dx, Vx> ?
    val input: VectorSpace<T1, D1, V1>
    val output: VectorSpace<T2, D2, V2>
}

typealias Transformation<T, D, V> = Relation<T, D, V, T, D, V>

// TODO is it enough to have just typealias?
//interface Transformation<T, D : Dimension, V : Vector<T, D>> : Relation<T, D, V, T, D, V> {
//    override operator fun invoke(v: V): V
//}