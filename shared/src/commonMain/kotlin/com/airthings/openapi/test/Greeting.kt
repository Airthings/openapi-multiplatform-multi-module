package com.airthings.openapi.test

import org.openapitools.client.apis.PetApi

class Greeting {
    @Throws(Throwable::class)
    suspend fun greetPet(): String {
        val petName = PetApi().findPetsByStatus(listOf("available")).body().firstOrNull()?.name ?: "??"
        return "Hello $petName! From ${Platform().platform}."
    }
}