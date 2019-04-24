pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        //maven ("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlinx-atomicfu" -> {
                    useModule("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${requested.version}")
                }
                "kotlin-multiplatform" ->{
                    useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                }
            }
        }
    }
}

enableFeaturePreview("GRADLE_METADATA")

rootProject.name = "kmath"
include(
    ":kmath-memory",
    ":kmath-core",
//    ":kmath-io",
    ":kmath-coroutines",
    ":kmath-histograms",
    ":kmath-commons",
    ":kmath-koma",
    ":kmath-streaming",
    ":benchmarks"
)
