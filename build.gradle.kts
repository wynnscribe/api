plugins {
    alias(libs.plugins.shadowJar) apply false
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
}

allprojects {
    group = property("maven_group").toString()
    version = property("mod_version").toString()

    group = "com.wynnscribe"

    repositories {
        mavenCentral()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}