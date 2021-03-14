package space.kscience.kmath.tensors

// https://proofwiki.org/wiki/Definition:Algebra_over_Ring
public interface TensorAlgebra<T, TensorType : TensorStructure<T>> {

    public fun TensorType.value(): T

    public fun zeros(shape: IntArray): TensorType
    public fun TensorType.zeroesLike(): TensorType
    public fun ones(shape: IntArray): TensorType
    public fun TensorType.onesLike(): TensorType


    //https://pytorch.org/docs/stable/generated/torch.full.html
    public fun full(shape: IntArray, value: T): TensorType

    //https://pytorch.org/docs/stable/generated/torch.full_like.html#torch.full_like
    public fun TensorType.fullLike(value: T): TensorType

    public fun TensorType.copy(): TensorType

    public operator fun T.plus(other: TensorType): TensorType
    public operator fun TensorType.plus(value: T): TensorType
    public operator fun TensorType.plus(other: TensorType): TensorType
    public operator fun TensorType.plusAssign(value: T): Unit
    public operator fun TensorType.plusAssign(other: TensorType): Unit

    public operator fun T.minus(other: TensorType): TensorType
    public operator fun TensorType.minus(value: T): TensorType
    public operator fun TensorType.minus(other: TensorType): TensorType
    public operator fun TensorType.minusAssign(value: T): Unit
    public operator fun TensorType.minusAssign(other: TensorType): Unit

    public operator fun T.times(other: TensorType): TensorType
    public operator fun TensorType.times(value: T): TensorType
    public operator fun TensorType.times(other: TensorType): TensorType
    public operator fun TensorType.timesAssign(value: T): Unit
    public operator fun TensorType.timesAssign(other: TensorType): Unit
    public operator fun TensorType.unaryMinus(): TensorType


    //https://pytorch.org/docs/stable/generated/torch.transpose.html
    public fun TensorType.transpose(i: Int, j: Int): TensorType

    //https://pytorch.org/docs/stable/tensor_view.html
    public fun TensorType.view(shape: IntArray): TensorType
    public fun TensorType.viewAs(other: TensorType): TensorType

    //https://pytorch.org/docs/stable/generated/torch.abs.html
    public fun TensorType.abs(): TensorType

    //https://pytorch.org/docs/stable/generated/torch.sum.html
    public fun TensorType.sum(dim: Int, keepDim: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.cumsum.html#torch.cumsum
    public fun TensorType.cumsum(dim: Int): TensorType

    //https://pytorch.org/docs/stable/generated/torch.prod.html#torch.prod
    public fun TensorType.prod(dim: Int, keepDim: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.cumprod.html#torch.cumprod
    public fun TensorType.cumprod(dim: Int): TensorType

    //https://pytorch.org/docs/stable/generated/torch.max.html#torch.max
    public fun TensorType.max(dim: Int, keepDim: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.cummax.html#torch.cummax
    public fun TensorType.cummax(dim: Int): TensorType

    //https://pytorch.org/docs/stable/generated/torch.min.html#torch.min
    public fun TensorType.min(dim: Int, keepDim: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.cummin.html#torch.cummin
    public fun TensorType.cummin(dim: Int): TensorType

    //https://pytorch.org/docs/stable/generated/torch.median.html#torch.median
    public fun TensorType.median(dim: Int, keepDim: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.maximum.html#torch.maximum
    public fun maximum(lhs: TensorType, rhs: TensorType)

    //https://pytorch.org/docs/stable/generated/torch.minimum.html#torch.minimum
    public fun minimum(lhs: TensorType, rhs: TensorType)

    //https://pytorch.org/docs/stable/generated/torch.sort.html#torch.sort
    public fun TensorType.sort(dim: Int, keepDim: Boolean, descending: Boolean): TensorType

    //https://pytorch.org/docs/stable/generated/torch.cat.html#torch.cat
    public fun cat(tensors: List<TensorType>, dim: Int): TensorType

    //https://pytorch.org/docs/stable/generated/torch.flatten.html#torch.flatten
    public fun TensorType.flatten(startDim: Int, endDim: Int): TensorType

}
