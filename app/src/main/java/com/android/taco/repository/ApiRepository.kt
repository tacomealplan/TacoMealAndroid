package com.android.taco.repository

import com.android.taco.model.*
import com.android.taco.remote.Api
import com.android.taco.util.Resource
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