plugins {
    base
    kotlin("jvm") version "1.5.31"
    kotlin("kapt") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

allprojects {
    group = "com.cookapi"
    version = "1.0.0"

    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
}
