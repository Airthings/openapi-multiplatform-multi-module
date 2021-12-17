package com.airthings.openapi.test

import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.TypeInfo
import io.ktor.http.Headers
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import org.openapitools.client.apis.PetApi
import org.openapitools.client.infrastructure.BodyProvider
import org.openapitools.client.infrastructure.HttpResponse
import org.openapitools.client.models.Pet

class GreeterTest {
    @Test
    fun testGreetPet() {
        val petName = "Rex"
        val petApi = PetApiStub(
            findPetsByStatusResponse = listOf(
                Pet(
                    name = petName,
                    photoUrls = emptyList()
                )
            )
        )
        val greeter = Greeter(petApi = petApi)
        runBlocking {
            assertTrue { greeter.greetPet().startsWith("Hello $petName!") }
        }
    }
}

private class PetApiStub(private val findPetsByStatusResponse: List<Pet>) : PetApi() {
    override suspend fun findPetsByStatus(status: List<String>): HttpResponse<List<Pet>> =
        HttpResponseStub(responseStub = findPetsByStatusResponse)
}

private class HttpResponseStub<T : Any>(responseStub: T) : HttpResponse<T>(
    response = KtorResponseStub(),
    provider = BodyProviderStub(responseStub = responseStub),
)

private class BodyProviderStub<T : Any>(val responseStub: T) : BodyProvider<T> {
    override suspend fun body(response: io.ktor.client.statement.HttpResponse): T = responseStub
    override suspend fun <V : Any> typedBody(
        response: io.ktor.client.statement.HttpResponse,
        type: TypeInfo
    ): V = TODO("Not yet implemented")
}

private class KtorResponseStub : io.ktor.client.statement.HttpResponse() {
    override val call: HttpClientCall get() = TODO("Not yet implemented")
    override val content: ByteReadChannel get() = TODO("Not yet implemented")
    override val coroutineContext: CoroutineContext get() = TODO("Not yet implemented")
    override val headers: Headers = Headers.Empty
    override val requestTime: GMTDate get() = TODO("Not yet implemented")
    override val responseTime: GMTDate get() = TODO("Not yet implemented")
    override val status: HttpStatusCode = HttpStatusCode.OK
    override val version: HttpProtocolVersion get() = TODO("Not yet implemented")
}
