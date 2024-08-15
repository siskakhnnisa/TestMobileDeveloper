package com.siskakhnnisa.userapp.ui.third

import androidx.lifecycle.ViewModel
import com.siskakhnnisa.userapp.repository.UserRepository

class ThirdViewModel (private val userRepo : UserRepository) : ViewModel() {

    fun getUsers(page: Int, perPage: Int) = userRepo.getUsers(page, perPage)
}