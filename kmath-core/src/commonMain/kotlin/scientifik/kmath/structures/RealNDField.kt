package scientifik.kmath.structures

import scientifik.kmath.operations.RealField

typealias RealNDElement = BufferNDElement<Double, RealField>

class RealNDField(shape: IntArray) :
    StridedNDField<Double, RealField>(shape, RealField),
    ExtendedNDField<Double, RealField, NDBuffer<Double>> {

    override fun buildBuffer(size: Int, initializer: (Int) -> Double): Buffer<Double> =
        DoubleBuffer(DoubleArray(size, initializer))

    /**
     * Inline transform an NDStructure to
     */
    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun map(
        arg: NDBuffer<Double>,
        crossinline transform: RealField.(Double) -> Double
    ): RealNDElement {
        check(arg)
        val array = DoubleArray(arg.strides.linearSize) { offset -> RealField.transform(arg.buffer[offset]) }
        return BufferNDElement(this, DoubleBuffer(array))
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun produce(crossinline initializer: RealField.(IntArray) -> Double): RealNDElement {
        val array = DoubleArray(strides.linearSize) { offset -> elementField.initializer(strides.index(offset)) }
        return BufferNDElement(this, DoubleBuffer(array))
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapIndexed(
        arg: NDBuffer<Double>,
        crossinline transform: RealField.(index: IntArray, Double) -> Double
    ): BufferNDElement<Double, RealField> {
        check(arg)
        return BufferNDElement(
            this,
            buildBuffer(arg.strides.linearSize) { offset ->
                elementField.transform(
                    arg.strides.index(offset),
                    arg.buffer[offset]
                )
            })
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun combine(
        a: NDBuffer<Double>,
        b: NDBuffer<Double>,
        crossinline transform: RealField.(Double, Double) -> Double
    ): BufferNDElement<Double, RealField> {
        check(a, b)
        return BufferNDElement(
            this,
            buildBuffer(strides.linearSize) { offset -> elementField.transform(a.buffer[offset], b.buffer[offset]) })
    }

    override fun power(arg: NDBuffer<Double>, pow: Double) = map(arg) { power(it, pow) }

    override fun exp(arg: NDBuffer<Double>) = map(arg) { exp(it) }

    override fun ln(arg: NDBuffer<Double>) = map(arg) { ln(it) }

    override fun sin(arg: NDBuffer<Double>) = map(arg) { sin(it) }

    override fun cos(arg: NDBuffer<Double>) = map(arg) { cos(it) }
//
//    override fun NDBuffer<Double>.times(k: Number) = mapInline { value -> value * k.toDouble() }
//
//    override fun NDBuffer<Double>.div(k: Number) = mapInline { value -> value / k.toDouble() }
//
//    override fun Number.times(b: NDBuffer<Double>) = b * this
//
//    override fun Number.div(b: NDBuffer<Double>) = b * (1.0 / this.toDouble())
}


/**
 * Fast element production using function inlining
 */
inline fun StridedNDField<Double, RealField>.produceInline(crossinline initializer: RealField.(Int) -> Double): RealNDElement {
    val array = DoubleArray(strides.linearSize) { offset -> elementField.initializer(offset) }
    return BufferNDElement(this, DoubleBuffer(array))
}

/**
 * Element by element application of any operation on elements to the whole array. Just like in numpy
 */
operator fun Function1<Double, Double>.invoke(ndElement: RealNDElement) =
    ndElement.context.produceInline { i -> invoke(ndElement.buffer[i]) }


/* plus and minus */

/**
 * Summation operation for [BufferNDElement] and single element
 */
operator fun RealNDElement.plus(arg: Double) =
    context.produceInline { i -> buffer[i] + arg }

/**
 * Subtraction operation between [BufferNDElement] and single element
 */
operator fun RealNDElement.minus(arg: Double) =
    context.produceInline { i -> buffer[i] - arg }