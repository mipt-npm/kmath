plugins {
    id("ru.mipt.npm.base")
    id("org.jetbrains.changelog") version "0.4.0"
}

val kmathVersion: String by extra("0.2.0-dev-1")
val bintrayRepo: String by extra("kscience")
val githubProject: String by extra("kmath")

allprojects {
    repositories {
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlinx")
        maven("https://dl.bintray.com/hotkeytlt/maven")
    }

    group = "kscience.kmath"
    version = kmathVersion
}

subprojects { if (name.startsWith("kmath")) apply(plugin = "ru.mipt.npm.publish") }
