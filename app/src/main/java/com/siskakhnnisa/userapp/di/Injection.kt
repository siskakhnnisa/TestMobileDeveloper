package com.siskakhnnisa.userapp.di

import com.siskakhnnisa.userapp.data.ApiConfig
import com.siskakhnnisa.userapp.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}