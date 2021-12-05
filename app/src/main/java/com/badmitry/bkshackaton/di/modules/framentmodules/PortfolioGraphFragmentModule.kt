package com.badmitry.bkshackaton.di.modules.framentmodules

import androidx.lifecycle.ViewModel
import com.badmitry.bkshackaton.di.annotations.ViewModelKey
import com.badmitry.bkshackaton.viewmodels.MainFragmentViewModel
import com.badmitry.bkshackaton.viewmodels.PortfolioGraphViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PortfolioGraphFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(PortfolioGraphViewModel::class)
    fun portfolioGraphViewModel(fragmentViewModel: PortfolioGraphViewModel): ViewModel
}
