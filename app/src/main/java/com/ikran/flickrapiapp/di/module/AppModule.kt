package com.ikran.flickrapiapp.di.module

import com.ikran.flickrapiapp.data.FlickrApi
import com.ikran.flickrapiapp.data.FlickrUtils
import com.ikran.flickrapiapp.repo.remote.SearchRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, VMModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideFlickrApiService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, FlickrApi::class.java)


    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(flickrApi: FlickrApi) = SearchRemoteDataSource(flickrApi)


    @Provides
    fun provideCoroutineScopeMain() = CoroutineScope(Dispatchers.Main)

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        java: Class<T>
    ): T {

        return createRetrofit(okHttpClient, converterFactory).create(java)
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FlickrUtils.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

}
