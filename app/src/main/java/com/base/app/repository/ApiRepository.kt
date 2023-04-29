package com.base.app.repository

import com.base.app.model.*
import com.base.app.remote.Api
import com.base.app.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.util.*
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