package com.ikran.flickrapiapp.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ikran.flickrapiapp.FlickrApiApp
import com.ikran.flickrapiapp.di.module.AppModule
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

    lateinit var component: AppComponent


    fun init(flickrApiApp: FlickrApiApp) {

        component =
            DaggerAppComponent.builder()
                .application(flickrApiApp)
                .appModule(AppModule())
                .build()

        component.inject(flickrApiApp)

        flickrApiApp.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityDestroyed(activity: Activity) {

                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                }

                override fun onActivityStopped(activity: Activity) {

                }

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityResumed(activity: Activity) {

                }

            }
        )
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }

                    }, true


                )
        }
    }
}