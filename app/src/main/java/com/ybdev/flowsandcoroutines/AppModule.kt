package com.ybdev.flowsandcoroutines

import com.ybdev.flowsandcoroutines.architecture.MyViewModel
import com.ybdev.flowsandcoroutines.architecture.repository.MyApi
import com.ybdev.flowsandcoroutines.architecture.repository.MyRepository
import com.ybdev.flowsandcoroutines.architecture.repository.MyRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

    single<MyRepository> {
        MyRepositoryImpl(get())
    }

    viewModel {
        MyViewModel(get())
    }
}