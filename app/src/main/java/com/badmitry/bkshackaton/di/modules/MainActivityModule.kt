package com.badmitry.bkshackaton.di.modules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun userDevicesViewModel(viewModel: MainViewModel): ViewModel
}
