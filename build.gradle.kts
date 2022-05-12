plugins {
    base
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

allprojects {
    group = "com.cookapi"
    version = "2.0.0"

    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
}
