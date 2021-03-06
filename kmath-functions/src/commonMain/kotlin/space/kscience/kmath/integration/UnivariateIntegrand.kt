/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package space.kscience.kmath.integration

import space.kscience.kmath.misc.UnstableKMathAPI
import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

public class UnivariateIntegrand<T : Any> internal constructor(
    private val features: Map<KClass<*>, IntegrandFeature>,
    public val function: (Double) -> T,
) : Integrand {

    @Suppress("UNCHECKED_CAST")
    override fun <T : IntegrandFeature> getFeature(type: KClass<T>): T? = features[type] as? T

    public operator fun <F : IntegrandFeature> plus(pair: Pair<KClass<out F>, F>): UnivariateIntegrand<T> =
        UnivariateIntegrand(features + pair, function)

    public operator fun <F : IntegrandFeature> plus(feature: F): UnivariateIntegrand<T> =
        plus(feature::class to feature)
}

@Suppress("FunctionName")
public fun <T : Any> UnivariateIntegrand(
    function: (Double) -> T,
    vararg features: IntegrandFeature,
): UnivariateIntegrand<T> = UnivariateIntegrand(features.associateBy { it::class }, function)

public typealias UnivariateIntegrator<T> = Integrator<UnivariateIntegrand<T>>

@JvmInline
public value class IntegrationRange(public val range: ClosedRange<Double>) : IntegrandFeature

public val <T : Any> UnivariateIntegrand<T>.value: T? get() = getFeature<IntegrandValue<T>>()?.value

/**
 * A shortcut method to integrate a [function] in [range] with additional [features].
 * The [function] is placed in the end position to allow passing a lambda.
 */
@UnstableKMathAPI
public fun UnivariateIntegrator<Double>.integrate(
    range: ClosedRange<Double>,
    vararg features: IntegrandFeature,
    function: (Double) -> Double,
): Double = integrate(
    UnivariateIntegrand(function, IntegrationRange(range), *features)
).value ?: error("Unexpected: no value after integration.")

/**
 * A shortcut method to integrate a [function] in [range] with additional [features].
 * The [function] is placed in the end position to allow passing a lambda.
 */
@UnstableKMathAPI
public fun UnivariateIntegrator<Double>.integrate(
    range: ClosedRange<Double>,
    function: (Double) -> Double,
    featureBuilder: (MutableList<IntegrandFeature>.() -> Unit) = {},
): Double {
    //TODO use dedicated feature builder class instead or add extensions to MutableList<IntegrandFeature>
    val features = buildList {
        featureBuilder()
        add(IntegrationRange(range))
    }
    return integrate(
        UnivariateIntegrand(function, *features.toTypedArray())
    ).value ?: error("Unexpected: no value after integration.")
}
