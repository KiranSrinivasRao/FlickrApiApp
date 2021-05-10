package com.ikran.flickrapiapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ikran.flickrapiapp.di.ViewModelFactory
import com.ikran.flickrapiapp.di.ViewModelKey
import com.ikran.flickrapiapp.search.SearchVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VMModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchVM::class)
    abstract fun bindSearchVM(searchVM: SearchVM) : ViewModel

}
