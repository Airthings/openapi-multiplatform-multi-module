pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

include(":androidApp")
include(":shared")
include(":openapi-generator:samples:client:petstore:kotlin-multiplatform")