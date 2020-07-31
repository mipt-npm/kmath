package scientifik.kmath.geometry.transformations

import scientifik.kmath.dimensions.Dimension
import scientifik.kmath.geometry.InnerProductSpace
import scientifik.kmath.geometry.Vector

/**
 * Interface to represent function over vectors with domain V1 and co-domain V2 (i.e. f: V1 -> V2)
 */
interface Relation<T1, D1 : Dimension, V1 : Vector<T1, D1>, T2, D2 : Dimension, V2 : Vector<T2, D2>> : (V1) -> V2 {
    /**
     * @param v vector from V1
     * @return vector from V2 associated to V1 by this relation
     */
    override operator fun invoke(v: V1): V2

    /**
     * Domain of the function
     */
    val input: InnerProductSpace<T1, D1, V1>

    /**
     * Co-domain of the function
     */
    val output: InnerProductSpace<T2, D2, V2>
}

/**
 * Function with coinciding domain and co-domain (i.e. f: V -> V)
 */
typealias Transformation<T, D, V> = Relation<T, D, V, T, D, V>
