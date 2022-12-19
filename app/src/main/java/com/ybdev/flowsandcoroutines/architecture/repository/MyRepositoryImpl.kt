package com.ybdev.flowsandcoroutines.architecture.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MyRepositoryImpl(private val api: MyApi) : MyRepository{

    override fun makeNetworkCall() {
        //api.callApi()

    }

    override fun fakeNetworkCall() = flow {
        delay(3000)
        emit("Network Call Success")
    }

}