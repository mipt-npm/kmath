/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package space.kscience.kmath.nd4j

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.api.ops.impl.summarystats.Variance
import org.nd4j.linalg.api.ops.impl.transforms.strict.ACosh
import org.nd4j.linalg.api.ops.impl.transforms.strict.ASinh
import org.nd4j.linalg.api.shape.Shape
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.factory.ops.NDBase
import org.nd4j.linalg.ops.transforms.Transforms
import space.kscience.kmath.misc.PerformancePitfall
import space.kscience.kmath.nd.StructureND
import space.kscience.kmath.samplers.GaussianSampler
import space.kscience.kmath.stat.RandomGenerator
import space.kscience.kmath.structures.toDoubleArray
import space.kscience.kmath.tensors.api.AnalyticTensorAlgebra
import space.kscience.kmath.tensors.api.Tensor
import space.kscience.kmath.tensors.api.TensorAlgebra
import space.kscience.kmath.tensors.core.DoubleTensor
import kotlin.math.abs

/**
 * ND4J based [TensorAlgebra] implementation.
 */
public sealed interface Nd4jTensorAlgebra<T : Number> : AnalyticTensorAlgebra<T> {
    /**
     * Wraps [INDArray] to [Nd4jArrayStructure].
     */
    public fun INDArray.wrap(): Nd4jArrayStructure<T>

    /**
     * Unwraps to or acquires [INDArray] from [StructureND].
     */
    public val StructureND<T>.ndArray: INDArray

    public override fun T.plus(other: Tensor<T>): Nd4jArrayStructure<T> = other.ndArray.add(this).wrap()
    public override fun Tensor<T>.plus(value: T): Nd4jArrayStructure<T> = ndArray.add(value).wrap()

    public override fun Tensor<T>.plus(other: Tensor<T>): Nd4jArrayStructure<T> = ndArray.add(other.ndArray).wrap()

    public override fun Tensor<T>.plusAssign(value: T) {
        ndArray.addi(value)
    }

    public override fun Tensor<T>.plusAssign(other: Tensor<T>) {
        ndArray.addi(other.ndArray)
    }

    public override fun T.minus(other: Tensor<T>): Nd4jArrayStructure<T> = other.ndArray.rsub(this).wrap()
    public override fun Tensor<T>.minus(value: T): Nd4jArrayStructure<T> = ndArray.sub(value).wrap()
    public override fun Tensor<T>.minus(other: Tensor<T>): Nd4jArrayStructure<T> = ndArray.sub(other.ndArray).wrap()

    public override fun Tensor<T>.minusAssign(value: T) {
        ndArray.rsubi(value)
    }

    public override fun Tensor<T>.minusAssign(other: Tensor<T>) {
        ndArray.subi(other.ndArray)
    }

    public override fun T.times(other: Tensor<T>): Nd4jArrayStructure<T> = other.ndArray.mul(this).wrap()

    public override fun Tensor<T>.times(value: T): Nd4jArrayStructure<T> =
        ndArray.mul(value).wrap()

    public override fun Tensor<T>.times(other: Tensor<T>): Nd4jArrayStructure<T> = ndArray.mul(other.ndArray).wrap()

    public override fun Tensor<T>.timesAssign(value: T) {
        ndArray.muli(value)
    }

    public override fun Tensor<T>.timesAssign(other: Tensor<T>) {
        ndArray.mmuli(other.ndArray)
    }

    public override fun Tensor<T>.unaryMinus(): Nd4jArrayStructure<T> = ndArray.neg().wrap()
    public override fun Tensor<T>.get(i: Int): Nd4jArrayStructure<T> = ndArray.slice(i.toLong()).wrap()
    public override fun Tensor<T>.transpose(i: Int, j: Int): Nd4jArrayStructure<T> = ndArray.swapAxes(i, j).wrap()
    public override fun Tensor<T>.dot(other: Tensor<T>): Nd4jArrayStructure<T> = ndArray.mmul(other.ndArray).wrap()

    public override fun Tensor<T>.min(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndArray.min(keepDim, dim).wrap()

    public override fun Tensor<T>.sum(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndArray.sum(keepDim, dim).wrap()

    public override fun Tensor<T>.max(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndArray.max(keepDim, dim).wrap()

    public override fun Tensor<T>.view(shape: IntArray): Nd4jArrayStructure<T> = ndArray.reshape(shape).wrap()
    public override fun Tensor<T>.viewAs(other: Tensor<T>): Nd4jArrayStructure<T> = view(other.shape)

    public override fun Tensor<T>.argMax(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndBase.argmax(ndArray, keepDim, dim).wrap()

    public override fun Tensor<T>.mean(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndArray.mean(keepDim, dim).wrap()

    public override fun Tensor<T>.exp(): Nd4jArrayStructure<T> = Transforms.exp(ndArray).wrap()
    public override fun Tensor<T>.ln(): Nd4jArrayStructure<T> = Transforms.log(ndArray).wrap()
    public override fun Tensor<T>.sqrt(): Nd4jArrayStructure<T> = Transforms.sqrt(ndArray).wrap()
    public override fun Tensor<T>.cos(): Nd4jArrayStructure<T> = Transforms.cos(ndArray).wrap()
    public override fun Tensor<T>.acos(): Nd4jArrayStructure<T> = Transforms.acos(ndArray).wrap()
    public override fun Tensor<T>.cosh(): Nd4jArrayStructure<T> = Transforms.cosh(ndArray).wrap()

    public override fun Tensor<T>.acosh(): Nd4jArrayStructure<T> =
        Nd4j.getExecutioner().exec(ACosh(ndArray, ndArray.ulike())).wrap()

    public override fun Tensor<T>.sin(): Nd4jArrayStructure<T> = Transforms.sin(ndArray).wrap()
    public override fun Tensor<T>.asin(): Nd4jArrayStructure<T> = Transforms.asin(ndArray).wrap()
    public override fun Tensor<T>.sinh(): Nd4jArrayStructure<T> = Transforms.sinh(ndArray).wrap()

    public override fun Tensor<T>.asinh(): Nd4jArrayStructure<T> =
        Nd4j.getExecutioner().exec(ASinh(ndArray, ndArray.ulike())).wrap()

    public override fun Tensor<T>.tan(): Nd4jArrayStructure<T> = Transforms.tan(ndArray).wrap()
    public override fun Tensor<T>.atan(): Nd4jArrayStructure<T> = Transforms.atan(ndArray).wrap()
    public override fun Tensor<T>.tanh(): Nd4jArrayStructure<T> = Transforms.tanh(ndArray).wrap()
    public override fun Tensor<T>.atanh(): Nd4jArrayStructure<T> = Transforms.atanh(ndArray).wrap()
    public override fun Tensor<T>.ceil(): Nd4jArrayStructure<T> = Transforms.ceil(ndArray).wrap()
    public override fun Tensor<T>.floor(): Nd4jArrayStructure<T> = Transforms.floor(ndArray).wrap()

    public override fun Tensor<T>.std(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        ndArray.std(true, keepDim, dim).wrap()

    public override fun T.div(other: Tensor<T>): Nd4jArrayStructure<T> = other.ndArray.rdiv(this).wrap()
    public override fun Tensor<T>.div(value: T): Nd4jArrayStructure<T> = ndArray.div(value).wrap()
    public override fun Tensor<T>.div(other: Tensor<T>): Nd4jArrayStructure<T> = ndArray.div(other.ndArray).wrap()

    public override fun Tensor<T>.divAssign(value: T) {
        ndArray.divi(value)
    }

    public override fun Tensor<T>.divAssign(other: Tensor<T>) {
        ndArray.divi(other.ndArray)
    }

    public override fun Tensor<T>.variance(dim: Int, keepDim: Boolean): Nd4jArrayStructure<T> =
        Nd4j.getExecutioner().exec(Variance(ndArray, true, true, dim)).wrap()

    private companion object {
        private val ndBase = NDBase()
    }
}


private fun minusIndexFrom(n: Int, i: Int): Int = if (i >= 0) i else {
    val ii = n + i
    check(ii >= 0) { "Out of bound index $i for tensor of dim $n" }
    ii
}

private fun getRandomNormals(n: Int, seed: Long): DoubleArray {
    val distribution = GaussianSampler(0.0, 1.0)
    val generator = RandomGenerator.default(seed)
    return distribution.sample(generator).nextBufferBlocking(n).toDoubleArray()
}


/**
 * [Double] specialization of [Nd4jTensorAlgebra].
 */
public object DoubleNd4jTensorAlgebra : Nd4jTensorAlgebra<Double> {
    public override fun INDArray.wrap(): Nd4jArrayStructure<Double> = asDoubleStructure()

    @OptIn(PerformancePitfall::class)
    public override val StructureND<Double>.ndArray: INDArray
        get() = when (this) {
            is Nd4jArrayStructure<Double> -> ndArray
            else -> Nd4j.zeros(*shape).also {
                elements().forEach { (idx, value) -> it.putScalar(idx, value) }
            }
        }

    public override fun Tensor<Double>.valueOrNull(): Double? =
        if (shape contentEquals intArrayOf(1)) ndArray.getDouble(0) else null

    public override fun diagonalEmbedding(
        diagonalEntries: Tensor<Double>,
        offset: Int,
        dim1: Int,
        dim2: Int,
    ): Tensor<Double> {
        val diagonalEntriesNDArray = diagonalEntries.ndArray
        val n = diagonalEntries.shape.size
        val d1 = minusIndexFrom(n + 1, dim1)
        val d2 = minusIndexFrom(n + 1, dim2)
        check(d1 != d2) { "Diagonal dimensions cannot be identical $d1, $d2" }
        check(d1 <= n && d2 <= n) { "Dimension out of range" }
        var lessDim = d1
        var greaterDim = d2
        var realOffset = offset

        if (lessDim > greaterDim) {
            realOffset *= -1
            lessDim = greaterDim.also { greaterDim = lessDim }
        }

        val resShape = diagonalEntries.shape.sliceArray(0 until lessDim) +
                intArrayOf(diagonalEntries.shape[n - 1] + abs(realOffset)) +
                diagonalEntries.shape.sliceArray(lessDim until greaterDim - 1) +
                intArrayOf(diagonalEntries.shape[n - 1] + abs(realOffset)) +
                diagonalEntries.shape.sliceArray(greaterDim - 1 until n - 1)
        val resTensor = Nd4j.zeros(*resShape).wrap()

        for (i in 0 until diagonalEntriesNDArray.length()) {
            val multiIndex = (if (diagonalEntriesNDArray.ordering() == 'c')
                Shape.ind2subC(diagonalEntriesNDArray, i)
            else
                Shape.ind2sub(diagonalEntriesNDArray, i)).toIntArray()

            var offset1 = 0
            var offset2 = abs(realOffset)
            if (realOffset < 0) offset1 = offset2.also { offset2 = offset1 }

            val diagonalMultiIndex = multiIndex.sliceArray(0 until lessDim) +
                    intArrayOf(multiIndex[n - 1] + offset1) +
                    multiIndex.sliceArray(lessDim until greaterDim - 1) +
                    intArrayOf(multiIndex[n - 1] + offset2) +
                    multiIndex.sliceArray(greaterDim - 1 until n - 1)

            resTensor[diagonalMultiIndex] = diagonalEntries[multiIndex]
        }

        return resTensor
    }

    /**
     * Compares element-wise two tensors with a specified precision.
     *
     * @param other the tensor to compare with `input` tensor.
     * @param epsilon permissible error when comparing two Double values.
     * @return true if two tensors have the same shape and elements, false otherwise.
     */
    public fun Tensor<Double>.eq(other: Tensor<Double>, epsilon: Double): Boolean =
        ndArray.equalsWithEps(other, epsilon)

    /**
     * Compares element-wise two tensors.
     * Comparison of two Double values occurs with 1e-5 precision.
     *
     * @param other the tensor to compare with `input` tensor.
     * @return true if two tensors have the same shape and elements, false otherwise.
     */
    public infix fun Tensor<Double>.eq(other: Tensor<Double>): Boolean = eq(other, 1e-5)

    public override fun Tensor<Double>.sum(): Double = ndArray.sumNumber().toDouble()
    public override fun Tensor<Double>.min(): Double = ndArray.minNumber().toDouble()
    public override fun Tensor<Double>.max(): Double = ndArray.maxNumber().toDouble()
    public override fun Tensor<Double>.mean(): Double = ndArray.meanNumber().toDouble()
    public override fun Tensor<Double>.std(): Double = ndArray.stdNumber().toDouble()
    public override fun Tensor<Double>.variance(): Double = ndArray.varNumber().toDouble()

    /**
     * Constructs a tensor with the specified shape and data.
     *
     * @param shape the desired shape for the tensor.
     * @param buffer one-dimensional data array.
     * @return tensor with the [shape] shape and [buffer] data.
     */
    public fun fromArray(shape: IntArray, buffer: DoubleArray): Nd4jArrayStructure<Double> =
        Nd4j.create(buffer, shape).wrap()

    /**
     * Constructs a tensor with the specified shape and initializer.
     *
     * @param shape the desired shape for the tensor.
     * @param initializer mapping tensor indices to values.
     * @return tensor with the [shape] shape and data generated by the [initializer].
     */
    public fun produce(shape: IntArray, initializer: (IntArray) -> Double): Nd4jArrayStructure<Double> {
        val struct = Nd4j.create(*shape)!!.wrap()
        struct.indicesIterator().forEach { struct[it] = initializer(it) }
        return struct
    }

    /**
     * Returns a tensor of random numbers drawn from normal distributions with 0.0 mean and 1.0 standard deviation.
     *
     * @param shape the desired shape for the output tensor.
     * @param seed the random seed of the pseudo-random number generator.
     * @return tensor of a given shape filled with numbers from the normal distribution
     * with 0.0 mean and 1.0 standard deviation.
     */
    public fun randomNormal(shape: IntArray, seed: Long = 0): Nd4jArrayStructure<Double> =
        fromArray(shape, getRandomNormals(shape.reduce(Int::times), seed))

    /**
     * Returns a tensor with the same shape as `input` of random numbers drawn from normal distributions
     * with 0.0 mean and 1.0 standard deviation.
     *
     * @param seed the random seed of the pseudo-random number generator.
     * @return tensor with the same shape as `input` filled with numbers from the normal distribution
     * with 0.0 mean and 1.0 standard deviation.
     */
    public fun Tensor<Double>.randomNormalLike(seed: Long = 0): Nd4jArrayStructure<Double> =
        fromArray(shape, getRandomNormals(shape.reduce(Int::times), seed))

    /**
     * Creates a tensor of a given shape and fills all elements with a given value.
     *
     * @param value the value to fill the output tensor with.
     * @param shape array of integers defining the shape of the output tensor.
     * @return tensor with the [shape] shape and filled with [value].
     */
    public fun full(value: Double, shape: IntArray): Nd4jArrayStructure<Double> = Nd4j.valueArrayOf(shape, value).wrap()

    /**
     * Returns a tensor with the same shape as `input` filled with [value].
     *
     * @param value the value to fill the output tensor with.
     * @return tensor with the `input` tensor shape and filled with [value].
     */
    public fun Tensor<Double>.fullLike(value: Double): Nd4jArrayStructure<Double> =
        Nd4j.valueArrayOf(ndArray.shape(), value).wrap()

    /**
     * Returns a tensor filled with the scalar value 0.0, with the shape defined by the variable argument [shape].
     *
     * @param shape array of integers defining the shape of the output tensor.
     * @return tensor filled with the scalar value 0.0, with the [shape] shape.
     */
    public fun zeros(shape: IntArray): Nd4jArrayStructure<Double> = full(0.0, shape)

    /**
     * Returns a tensor filled with the scalar value 0.0, with the same shape as a given array.
     *
     * @return tensor filled with the scalar value 0.0, with the same shape as `input` tensor.
     */
    public fun Tensor<Double>.zeroesLike(): Nd4jArrayStructure<Double> = Nd4j.zerosLike(ndArray).wrap()

    /**
     * Returns a tensor filled with the scalar value 1.0, with the shape defined by the variable argument [shape].
     *
     * @param shape array of integers defining the shape of the output tensor.
     * @return tensor filled with the scalar value 1.0, with the [shape] shape.
     */
    public fun ones(shape: IntArray): Nd4jArrayStructure<Double> = Nd4j.ones(*shape).wrap()

    /**
     * Returns a tensor filled with the scalar value 1.0, with the same shape as a given array.
     *
     * @return tensor filled with the scalar value 1.0, with the same shape as `input` tensor.
     */
    public fun Tensor<Double>.onesLike(): Nd4jArrayStructure<Double> = Nd4j.onesLike(ndArray).wrap()
}
