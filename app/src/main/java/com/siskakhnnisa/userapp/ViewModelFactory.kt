package com.siskakhnnisa.userapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siskakhnnisa.userapp.di.Injection
import com.siskakhnnisa.userapp.repository.UserRepository
import com.siskakhnnisa.userapp.ui.third.ThirdViewModel

class ViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            return ThirdViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUserRepository())
            }.also { instance = it }
    }
}