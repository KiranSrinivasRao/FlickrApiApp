package com.ikran.flickrapiapp

import android.app.Activity
import android.app.Application
import com.ikran.flickrapiapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class FlickrApiApp : Application(), HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }


}