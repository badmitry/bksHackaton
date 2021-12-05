package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.SubFragmentPayoutsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SubFragmentPayoutsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SubFragmentPayoutsViewModel::class)
    fun subFragmentPayoutsViewModel(fragmentPayoutsViewModelSubFragment: SubFragmentPayoutsViewModel): ViewModel
}
