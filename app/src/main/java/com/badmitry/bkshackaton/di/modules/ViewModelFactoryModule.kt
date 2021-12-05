package com.badmitry.bkshackaton.di.modules

import androidx.lifecycle.ViewModelProvider
import com.badmitry.bkshackaton.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
