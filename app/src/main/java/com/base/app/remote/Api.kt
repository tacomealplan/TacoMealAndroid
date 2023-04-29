package com.base.app.remote

import com.base.app.model.*
import retrofit2.http.*

interface Api {
    @POST("InsertTable/4D1C28A3-7CFA-44F0-B92F-3EAEFE4D71CC")
    suspend fun request(
        @Body() body : RequestBody
    ): CommonResponse
}