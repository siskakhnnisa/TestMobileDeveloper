package com.siskakhnnisa.userapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import com.siskakhnnisa.userapp.paging.UserSourcePaging
import com.siskakhnnisa.userapp.model.DataItem
import com.siskakhnnisa.userapp.data.ApiService
import com.siskakhnnisa.userapp.Result
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.Dispatchers

class UserRepository private constructor(
    private var apiService: ApiService
){
    fun getUsers(page: Int, perPage: Int): LiveData<Result<PagingData<DataItem>>> = liveData(Dispatchers.IO){
        emit(Result.Loading)
        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = perPage
                ),
                pagingSourceFactory = {
                    UserSourcePaging(apiService,page)
                }
            ).liveData
            emit(Result.Success(pager))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService).also { instance = it }
            }
    }
}