
import space.kscience.kmath.operations.RealField
import space.kscience.kmath.units.MeasurementExtendedField
import space.kscience.kmath.units.Pa
import kotlin.random.Random
import kotlin.time.measureTime

public fun main() {
    var rng = Random(0)
    var sum1 = 0.0

    measureTime {
        repeat(10000000) { sum1 += rng.nextDouble() }
    }.also(::println)

    println(sum1)

    rng = Random(0)

    with(MeasurementExtendedField(RealField)) {
        var sum2 = 0.0 * Pa

        measureTime {
            repeat(10000000) { sum2 += rng.nextDouble() * Pa }
        }.also(::println)

        println(sum2)
    }
}
