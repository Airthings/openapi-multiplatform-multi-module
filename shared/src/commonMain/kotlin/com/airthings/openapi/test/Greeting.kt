package com.airthings.openapi.test

import io.ktor.client.HttpClientConfig
import io.ktor.client.features.UserAgent
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.openapitools.client.apis.PetApi

class Greeting {

    private val petApi = PetApi(httpClientConfig = createHttpClientConfig())

    private fun createHttpClientConfig(): ((HttpClientConfig<*>) -> Unit) =
            { clientConfig ->
                clientConfig.install(UserAgent) {
                    agent = "my-pet-store-app/${Platform().platform}"
                }
                clientConfig.install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("HttpClient: $message")
                        }
                    }
                    level = LogLevel.ALL
                }
            }

    @Throws(Throwable::class)
    suspend fun greetPet(): String {
        val petName = petApi.findPetsByStatus(listOf("available")).body().firstOrNull()?.name ?: "??"
        return "Hello $petName! From ${Platform().platform}."
    }
}