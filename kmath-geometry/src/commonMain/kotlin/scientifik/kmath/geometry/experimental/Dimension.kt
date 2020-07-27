package scientifik.kmath.geometry.experimental

sealed class Dimension {
    abstract val size: Int
}

object D0 : Dimension() {
    override val size: Int = 0
}

open class HigherDimension<SubDim : Dimension>(subDim: SubDim) : Dimension() {
    override val size: Int = subDim.size + 1
}

object D1 : HigherDimension<D0>(D0)
object D2 : HigherDimension<D1>(D1)
object D3 : HigherDimension<D2>(D2)
object D4 : HigherDimension<D3>(D3)
object D5 : HigherDimension<D4>(D4)
object D6 : HigherDimension<D5>(D5)
object D7 : HigherDimension<D6>(D6)
object D8 : HigherDimension<D7>(D7)
object D9 : HigherDimension<D8>(D8)