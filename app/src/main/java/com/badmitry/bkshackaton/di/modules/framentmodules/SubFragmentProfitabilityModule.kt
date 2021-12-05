package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.SubFragmentProfitabilityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SubFragmentProfitabilityModule {
    @Binds
    @IntoMap
    @ViewModelKey(SubFragmentProfitabilityViewModel::class)
    fun subFragmentProfitabilityViewModel(fragmentProfitabilityViewModelSubFragment: SubFragmentProfitabilityViewModel): ViewModel
}
