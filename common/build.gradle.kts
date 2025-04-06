plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    `maven-publish`
}

group = "com.wynnscribe"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.kotlin.serializationJson)
    compileOnly(libs.kotlinx.datetime)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Wynnscribe Common")
                description.set("Wynnscribeの共有ライブラリです。APIサーバーのスキーマや共通のユーティリティが入っています。 Wynnscribe's shared library containing API server schema and common utilities.")
            }
        }
    }
}