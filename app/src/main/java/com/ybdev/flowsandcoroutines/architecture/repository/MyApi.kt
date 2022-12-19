package com.ybdev.flowsandcoroutines.architecture.repository

import retrofit2.http.GET

interface MyApi {

    @GET("...")
    fun callApi()
}