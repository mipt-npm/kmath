package kscience.kmath.structures

import kscience.kmath.operations.Complex
import kscience.kmath.operations.complex
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class BufferBenchmark {

    @Benchmark
    fun genericRealBufferReadWrite() {
        val buffer = RealBuffer(size) { it.toDouble() }

        (0 until size).forEach {
            buffer[it]
        }
    }

    @Benchmark
    fun complexBufferReadWrite() {
        val buffer = MutableBuffer.complex(size / 2) { Complex(it.toDouble(), -it.toDouble()) }

        (0 until size / 2).forEach {
            buffer[it]
        }
    }

    companion object {
        const val size: Int = 100
    }
}