package scientifik.kmath.geometry

import scientifik.kmath.dimensions.D4
import scientifik.kmath.geometry.transformations.mapTo
import scientifik.kmath.geometry.transformations.projection
import scientifik.kmath.geometry.transformations.shiftBy
import scientifik.kmath.operations.RealField
import scientifik.kmath.operations.invoke

fun main2() {
    (EuclideanSpace(D4, RealField)) {
        val a: VectorImpl<Double, D4> = VectorImpl(listOf(1.0, 2.0, 3.0, 5.0))
        val b = vectorOf(2.0, -3.0, 4.0, 5.0)
        println(a - b)
        println(a + b)
        println(a * 4)
        println(a dot b)
        println(a.distanceTo(b))
        println(distance(a, b))

        println(zero)
    }

    Real4DVectorSpace {
        val a = vectorOf(1.0, 2.0, 3.0, 5.0)
        val b = vectorOf(2.0, -3.0, 4.0, 5.0)

        val transformation = projection(vectorOf(0.0, 0.0, 1.0, 0.0), zero)
                .mapTo(Real3DVectorSpace) { vector -> vectorOf(vector[0], vector[1], vector[3]) }
                .shiftBy(vectorOf(1.0, -2.0, 0.01))

        val distance = Real3DVectorSpace.distance(
                transformation(a + 3 * b),
                transformation((a dot b) * b - a / norm(b))
        )

        println("""Resulting distance: $distance""")
    }
}

fun main() {
    Euclidean3DSpace {
        val a = Vector3D(1.0, -2.0, 0.01)
        val b = Vector3D(1.0, 2.0, 3.0)

        println(a - b)
        println(a + b)
        println(a * 4)
        println(a dot b)
        println(a.distanceTo(b))
        println(distance(a, b))

        val transformation = projection(Vector3D(0.0, 1.0, 0.0), zero)
                .mapTo(Euclidean2DSpace) { vector -> Vector2D(vector[0], vector[2]) }
                .shiftBy(Vector2D(5.0, -9.9))

        val distance = Euclidean2DSpace.distance(
                transformation(a + 3 * b),
                transformation((a dot b) * b - a / norm(b))
        )

        println(distance)
    }
}
