package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.OptionsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OptionsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(OptionsFragmentViewModel::class)
    fun optionsViewModel(fragmentViewModel: OptionsFragmentViewModel): ViewModel
}
