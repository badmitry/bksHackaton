package com.badmitry.bkshackaton.di.modules

import com.badmitry.bkshackaton.MainActivity
import com.badmitry.bkshackaton.di.modules.framentmodules.*
import com.badmitry.bkshackaton.fragments.FragmentMain
import com.badmitry.bkshackaton.fragments.FragmentOptions
import com.badmitry.bkshackaton.fragments.FragmentPortfolioGraph
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentPayouts
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentPortfolio
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentProfitability
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ComponentsModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    fun fragmentMain(): FragmentMain

    @ContributesAndroidInjector(modules = [OptionsFragmentModule::class])
    fun fragmentOptions(): FragmentOptions

    @ContributesAndroidInjector(modules = [PortfolioGraphFragmentModule::class])
    fun fragmentPortfolioGraph(): FragmentPortfolioGraph

    @ContributesAndroidInjector(modules = [SubFragmentProfitabilityModule::class])
    fun subFragmentProfitability(): SubFragmentProfitability

    @ContributesAndroidInjector(modules = [SubFragmentPayoutsModule::class])
    fun subFragmentPayouts(): SubFragmentPayouts

    @ContributesAndroidInjector(modules = [SubFragmentPortfolioModule::class])
    fun subFragmentPortfolio(): SubFragmentPortfolio
}
