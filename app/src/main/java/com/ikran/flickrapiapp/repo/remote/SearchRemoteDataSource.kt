package com.ikran.flickrapiapp.repo.remote

import com.example.flickr.data.Result
import com.ikran.flickrapiapp.data.FlickrApi
import com.ikran.flickrapiapp.data.FlickrApiResult
import com.ikran.flickrapiapp.data.FlickrUtils
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(val service: FlickrApi) : BaseDataSource() {

    val map = HashMap<String, String>()

    init {
        map["method"] = FlickrUtils.METHOD_SEARCH
        map["api_key"] = FlickrUtils.API_KEY
        map["format"] = FlickrUtils.FORMAT
    }

    fun search(requestedLoadSize: Int, query: String, page: Int): Result<FlickrApiResult> {

        map["text"] = query
        return getResult { service.searchPhotos(requestedLoadSize, page, map) }
    }

}