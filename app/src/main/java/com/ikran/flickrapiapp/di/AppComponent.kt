package com.ikran.flickrapiapp.di

import android.app.Application
import com.ikran.flickrapiapp.FlickrApiApp
import com.ikran.flickrapiapp.di.module.ActivityModule
import com.ikran.flickrapiapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(flickrApiApp: FlickrApiApp)

}
