package com.ikran.flickrapiapp.repo.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.flickr.data.Result
import com.ikran.flickrapiapp.data.NetworkState
import com.ikran.flickrapiapp.data.Photo
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val query: String
) : PageKeyedDataSource<Int, Photo>() {
    val networkState = MutableLiveData<NetworkState>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {

        networkState.postValue(NetworkState.LOADING)

        fetchData(query, 1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {

        networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(query, page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }

    }

    private fun fetchData(
        query: String,
        page: Int,
        requestedLoadSize: Int,
        callback: (List<Photo>) -> Unit
    ) {
        val response = searchRemoteDataSource.search(requestedLoadSize, query, page)

        Log.e("SearchDataSource", "${response.status}")

        if (response.status == Result.Status.SUCCESS) {

            val results = response.data?.photos?.photo

            if (results.isNullOrEmpty()) {
                networkState.postValue(NetworkState.noData())
            } else {
                callback(results)
                networkState.postValue(NetworkState.LOADED)
            }
        } else if (response.status == Result.Status.ERROR) {
            networkState.postValue(NetworkState.error(response.message ?: "Unknown Error"))
            postError(response.message!!)
        }
    }

    private fun postError(message: String) {
        networkState.postValue(NetworkState.error(message))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        // DO nothing
    }


}