package com.ikran.flickrapiapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FlickrApi {

    @GET(".")
    fun searchPhotos(
        @Query("per_page")perpage: Int,
        @Query("page")page:Int,
        @QueryMap options:Map<String, String>,
        @Query("nojsoncallback")noJson:Int =1
    ): Call<FlickrApiResult>

}
