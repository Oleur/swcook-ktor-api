import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

// Delegated extra properties referenced in gradle.properties
val kotlin: String by extra
val ktor: String by extra
val exposed: String by extra
val logback: String by extra
val koin: String by extra
val moshi: String by extra
val spek: String by extra

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

plugins {
    application
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.johnrengelman.shadow")
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDirs("src/main/kotlin")
    }
    sourceSets["test"].apply {
        kotlin.srcDirs("src/test/kotlin")
    }
}

sourceSets {
    main {
        resources.srcDir("src/main/resources")
    }
    test {
        resources.srcDirs("src/test/resources")
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")

    // Ktor
    implementation("io.ktor:ktor-server-netty:$ktor")
    implementation("io.ktor:ktor-locations:$ktor")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logback")

    // Koin
    implementation("org.koin:koin-ktor:$koin")
    implementation("org.koin:koin-logger-slf4j:$koin")

    // DB
    implementation("org.postgresql:postgresql:42.2.16")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.flywaydb:flyway-core:6.5.5")
    implementation("org.jetbrains.exposed:exposed-core:$exposed")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed")

    // Moshi
    implementation("com.squareup.moshi:moshi:$moshi")
    implementation("com.squareup.moshi:moshi-adapters:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    // Okio
    implementation("com.squareup.okio:okio:2.8.0")

    // Validation
    implementation("org.valiktor:valiktor-core:0.12.0")

    // Test
    testImplementation("io.ktor:ktor-server-tests:$ktor")
    testImplementation("org.koin:koin-test:$koin")

    testImplementation("io.mockk:mockk:1.10.0")

    testImplementation("org.amshove.kluent:kluent:1.61")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("swcook.jar")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
}
