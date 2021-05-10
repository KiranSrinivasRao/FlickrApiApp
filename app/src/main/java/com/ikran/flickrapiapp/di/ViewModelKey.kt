package com.ikran.flickrapiapp.di

import androidx.lifecycle.ViewModel
import com.ikran.flickrapiapp.search.SearchVM
import dagger.MapKey
import kotlin.reflect.KClass


@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER

)


@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
