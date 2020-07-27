package scientifik.kmath.geometry.experimental

// TODO think how we can add asserts about direction.norm() > 0
data class Line<T, D : Dimension, V : Vector<T, D>>(val base: V, val direction: V)

// TODO assert that p1 != p2
fun <T, D : Dimension, V : Vector<T, D>> VectorSpace<T, D, V>.lineThrough(p1: V, p2: V): Line<T, D, V> =
        Line(p1, with(this) { p2 - p1 })
