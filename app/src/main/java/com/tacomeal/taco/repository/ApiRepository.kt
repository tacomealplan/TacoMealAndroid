package com.tacomeal.taco.repository

import com.tacomeal.taco.model.*
import com.tacomeal.taco.remote.Api
import com.tacomeal.taco.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val api: Api
) {

    suspend fun makeRequest(id: String): Resource<CommonResponse> {
        val response = try {
            val body = RequestBody()
            body.add( "-")
            api.request(body = body)
        } catch(e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}