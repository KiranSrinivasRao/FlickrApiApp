package com.ikran.flickrapiapp.repo.remote

import com.example.flickr.data.Result
import retrofit2.Call
import java.lang.Exception

abstract class BaseDataSource {

    fun <T> getResult(call: () -> Call<T>): Result<T> {

        try {
            val response = call().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error("${response.code()}  ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    fun <T> error(message: String): Result<T> {
        return Result.error("Network call has failed - ${message}")
    }
}
