package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.SubFragmentPortfolioViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SubFragmentPortfolioModule {
    @Binds
    @IntoMap
    @ViewModelKey(SubFragmentPortfolioViewModel::class)
    fun subFragmentPortfolioViewModel(fragmentPortfolioViewModelSubFragment: SubFragmentPortfolioViewModel): ViewModel
}
