package kscience.kmath.gsl

import kotlinx.cinterop.memScoped
import org.gnu.gsl.gsl_block_calloc
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class ErrorHandler {
    @Test
    fun blockAllocation() {
        assertFailsWith<GslException> {
            ensureHasGslErrorHandler()
            gsl_block_calloc(ULong.MAX_VALUE)
        }
    }

    @Test
    fun matrixAllocation() {
        assertFailsWith<GslException> {
            memScoped { GslRealMatrixContext(this).produce(Int.MAX_VALUE, Int.MAX_VALUE) { _, _ -> 0.0 } }
        }
    }
}
