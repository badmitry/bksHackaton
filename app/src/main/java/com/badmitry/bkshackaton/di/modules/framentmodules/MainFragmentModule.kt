package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.MainFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    fun mainViewModel(fragmentViewModel: MainFragmentViewModel): ViewModel
}
