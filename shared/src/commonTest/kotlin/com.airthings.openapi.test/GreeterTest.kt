package com.airthings.openapi.test

import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import kotlinx.coroutines.runBlocking
import org.openapitools.client.apis.PetApi
import org.openapitools.client.infrastructure.HttpResponse
import org.openapitools.client.models.Pet
import kotlin.test.Test
import kotlin.test.assertEquals
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.http.Headers
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.http.headersOf
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openapitools.client.infrastructure.wrap
import kotlin.coroutines.CoroutineContext

class MockPetApi(
    engine: HttpClientEngine,
    private val findPetsByStatusResponse: List<Pet> = emptyList()
) : PetApi() {
    private val httpClient = HttpClient(engine)

    override suspend fun findPetsByStatus(status: List<String>): HttpResponse<List<Pet>> =
        httpClient.get

}

class GreeterTest {
    @Test
    fun renameMe() {
        val expected = ""

        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel(
                        Json.encodeToString(
                            listOf(
                                Pet(
                                    name = "Ape",
                                    photoUrls = emptyList()
                                )
                            )
                        )
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf("ContentType" to listOf("application/json"))
                )
            }
            val greeter = Greeter(
                petApi = MockPetApi(
                    engine = mockEngine
                )
            )
            val actual = greeter.greetPet()
            assertEquals(expected = expected, actual = actual)
        }
    }
}