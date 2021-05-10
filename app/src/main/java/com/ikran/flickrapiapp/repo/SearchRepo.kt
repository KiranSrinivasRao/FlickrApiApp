package com.ikran.flickrapiapp.repo

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.ikran.flickrapiapp.data.Data
import com.ikran.flickrapiapp.data.Photo
import com.ikran.flickrapiapp.repo.remote.SearchDataSourceFactory
import com.ikran.flickrapiapp.repo.remote.SearchRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(private val searchRemoteDataSource: SearchRemoteDataSource) {

    fun searchPhoto(query: String): Data<Photo> {
        Log.e("SearchRepo", " $query ")

        val sourceDataFactory = SearchDataSourceFactory(searchRemoteDataSource, query)


        val networkState = Transformations.switchMap(
            sourceDataFactory.liveData
        ) {
            it.networkState
        }

        return Data(
            LivePagedListBuilder(
                sourceDataFactory,
                SearchDataSourceFactory.pagedListConfig()
            ).build(), networkState
        )

    }
}