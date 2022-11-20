import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Delegated extra properties referenced in gradle.properties
val kotlin: String by extra
val ktor: String by extra
val exposed: String by extra
val logback: String by extra
val koin: String by extra
val moshi: String by extra
val spek: String by extra

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

plugins {
    application
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // Ktor
    implementation("io.ktor:ktor-server-netty:$ktor")
    implementation("io.ktor:ktor-server-resources:$ktor")
    implementation("io.ktor:ktor-server-status-pages:$ktor")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logback")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin")
    implementation("io.insert-koin:koin-logger-slf4j:$koin")

    // DB
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:9.8.1")
    implementation("org.jetbrains.exposed:exposed-core:$exposed")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed")

    // Moshi
    implementation("com.squareup.moshi:moshi:$moshi")
    implementation("com.squareup.moshi:moshi-adapters:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    // Okio
    implementation("com.squareup.okio:okio:3.2.0")

    // Validation
    implementation("org.valiktor:valiktor-core:0.12.0")

    // Test
    testImplementation("io.ktor:ktor-server-tests:$ktor")
    testImplementation("org.koin:koin-test:$koin")

    testImplementation("io.mockk:mockk:1.13.2")

    testImplementation("org.amshove.kluent:kluent:1.72")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin")
}

// Dirty hack find something better
gradle.taskGraph.whenReady {
    allTasks
        .filter { it.hasProperty("duplicatesStrategy") } // Because it's some weird decorated wrapper that I can't cast.
        .forEach {
            it.setProperty("duplicatesStrategy", "EXCLUDE")
        }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("cookapi.jar")
        mergeServiceFiles()
        manifest {
            attributes["Main-Class"] = "MainKt"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
