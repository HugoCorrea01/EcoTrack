// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.3.10" apply false

}

buildscript {
    repositories {
        gradlePluginPortal()  // Apenas o repositório do Gradle Plugin Portal
    }
    dependencies {
        // Adiciona o plugin Develocity
        classpath("com.gradle:develocity-gradle-plugin:3.17.5")

        // Outros plugins necessários
classpath("com.android.tools.build:gradle:8.9.0")  // Exemplo do plugin Android
}
}