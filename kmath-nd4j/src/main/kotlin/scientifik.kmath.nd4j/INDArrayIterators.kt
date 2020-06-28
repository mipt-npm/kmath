package scientifik.kmath.nd4j

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.api.shape.Shape

internal sealed class INDArrayIteratorBase<T>(protected val iterateOver: INDArray) : Iterator<Pair<IntArray, T>> {
    private var i: Int = 0

    override fun hasNext(): Boolean = i < iterateOver.length()

    abstract fun getSingle(indices: LongArray): T

    final override fun next(): Pair<IntArray, T> {
        val la = if (iterateOver.ordering() == 'c')
            Shape.ind2subC(iterateOver, i++.toLong())!!
        else
            Shape.ind2sub(iterateOver, i++.toLong())!!

        return narrowToIntArray(la) to getSingle(la)
    }
}

internal class INDArrayDoubleIterator(iterateOver: INDArray) : INDArrayIteratorBase<Double>(iterateOver) {
    override fun getSingle(indices: LongArray): Double = iterateOver.getDouble(*indices)
}

internal class INDArrayLongIterator(iterateOver: INDArray) : INDArrayIteratorBase<Long>(iterateOver) {
    override fun getSingle(indices: LongArray) = iterateOver.getLong(*indices)
}

internal class INDArrayIntIterator(iterateOver: INDArray) : INDArrayIteratorBase<Int>(iterateOver) {
    override fun getSingle(indices: LongArray) = iterateOver.getInt(*narrowToIntArray(indices))
}

internal class INDArrayFloatIterator(iterateOver: INDArray) : INDArrayIteratorBase<Float>(iterateOver) {
    override fun getSingle(indices: LongArray) = iterateOver.getFloat(*indices)
}