package scientifik.kmath.dimensions

import kotlin.reflect.KClass

/**
 * An abstract class which is not used in runtime. Designates a size of some structure.
 * Could be replaced later by fully inline constructs
 */
interface Dimension {

    val dim: UInt
    companion object {

    }
}

fun <D : Dimension> KClass<D>.dim(): UInt = Dimension.resolve(this).dim

expect fun <D : Dimension> Dimension.Companion.resolve(type: KClass<D>): D

expect fun Dimension.Companion.of(dim: UInt): Dimension

inline fun <reified D : Dimension> Dimension.Companion.dim(): UInt = D::class.dim()

object D0 : Dimension {
    override val dim: UInt get() = 0U
}

object D1 : Dimension {
    override val dim: UInt get() = 1U
}

object D2 : Dimension {
    override val dim: UInt get() = 2U
}

object D3 : Dimension {
    override val dim: UInt get() = 3U
}

object D4 : Dimension {
    override val dim: UInt get() = 4U
}

object D5 : Dimension {
    override val dim: UInt get() = 5U
}