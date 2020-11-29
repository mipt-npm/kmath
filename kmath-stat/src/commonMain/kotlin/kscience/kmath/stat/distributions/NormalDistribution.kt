package kscience.kmath.stat.distributions

import kscience.kmath.chains.Chain
import kscience.kmath.stat.RandomGenerator
import kscience.kmath.stat.UnivariateDistribution
import kscience.kmath.stat.internal.InternalErf
import kscience.kmath.stat.samplers.GaussianSampler
import kscience.kmath.stat.samplers.NormalizedGaussianSampler
import kscience.kmath.stat.samplers.ZigguratNormalizedGaussianSampler
import kotlin.math.*

public inline class NormalDistribution(public val sampler: GaussianSampler) : UnivariateDistribution<Double> {
    public constructor(
        mean: Double,
        standardDeviation: Double,
        normalized: NormalizedGaussianSampler = ZigguratNormalizedGaussianSampler.of(),
    ) : this(GaussianSampler.of(mean, standardDeviation, normalized))

    public override fun probability(arg: Double): Double {
        val x1 = (arg - sampler.mean) / sampler.standardDeviation
        return exp(-0.5 * x1 * x1 - (ln(sampler.standardDeviation) + 0.5 * ln(2 * PI)))
    }

    public override fun sample(generator: RandomGenerator): Chain<Double> = sampler.sample(generator)

    public override fun cumulative(arg: Double): Double {
        val dev = arg - sampler.mean

        return when {
            abs(dev) > 40 * sampler.standardDeviation -> if (dev < 0) 0.0 else 1.0
            else -> 0.5 * InternalErf.erfc(-dev / (sampler.standardDeviation * SQRT2))
        }
    }

    private companion object {
        private val SQRT2 = sqrt(2.0)
    }
}