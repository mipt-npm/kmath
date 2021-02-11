package kscience.kmath.histogram

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.getAndUpdate
import kscience.kmath.operations.Space

/*
 * Common representation for atomic counters
 * TODO replace with atomics
 */

public interface Counter<T : Any> {
    public fun add(delta: T)
    public val value: T
}

public class IntCounter : Counter<Int> {
    private val innerValue = atomic(0)

    override fun add(delta: Int) {
        innerValue += delta
    }

    override val value: Int get() = innerValue.value
}

public class LongCounter : Counter<Long> {
    private val innerValue = atomic(0L)

    override fun add(delta: Long) {
        innerValue += delta
    }

    override val value: Long get() = innerValue.value
}

public class ObjectCounter<T : Any>(public val space: Space<T>) : Counter<T> {
    private val innerValue = atomic(space.zero)

    override fun add(delta: T) {
        innerValue.getAndUpdate { space.run { it + delta } }
    }

    override val value: T get() = innerValue.value
}


