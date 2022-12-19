package com.ybdev.flowsandcoroutines.architecture.repository

import kotlinx.coroutines.flow.Flow

interface MyRepository {

    fun makeNetworkCall()
    fun fakeNetworkCall() : Flow<String>
}