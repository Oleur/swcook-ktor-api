plugins {
    base
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

allprojects {
    group = "com.innovorder"
    version = "1.0.0"

    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        jcenter()
    }
}
