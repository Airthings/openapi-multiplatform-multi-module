buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0") // kotlin_version
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}

plugins {
    kotlin("multiplatform") version "1.6.0" apply false // kotlin_version
    kotlin("plugin.serialization") version "1.6.0" apply false // kotlin_version
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}