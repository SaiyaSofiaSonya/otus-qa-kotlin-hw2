import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("io.qameta.allure") version "2.11.2"
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"
val springVersion = "2.7.6"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation(kotlin("test"))
//    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
//
   testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest.extensions:kotest-extensions-allure:1.2.0")
//
//    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.platform:junit-platform-suite:1.8.1")
    testImplementation("io.cucumber:cucumber-java8:7.0.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.0.0")


}

allure {
    report{
        version.set("2.20.1")
    }
}

//Rewrite config
detekt {
    toolVersion = "1.22.0"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    basePath = "$projectDir"
}

// Kotlin DSL
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("allureReport")
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}