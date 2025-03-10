pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")  // Adicionando o repositório JitPack
        gradlePluginPortal()  // Adiciona o repositório do Gradle Plugin Portal
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()  // Repositório do Google
        mavenCentral()  // Maven Central
        maven("https://jitpack.io")  // Adiciona o repositório JitPack
    }
}

rootProject.name = "EcoTrack"
include(":app")
