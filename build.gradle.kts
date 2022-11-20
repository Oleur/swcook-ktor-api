plugins {
    base
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

allprojects {
    group = "com.cookapi"
    version = "2.1.0"

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        mavenLocal()
    }
}
