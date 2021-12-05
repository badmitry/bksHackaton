package com.badmitry.bkshackaton.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavigationModule {

    @Singleton
    @Provides
    @Named("main")
    fun getMainCicerone() = Cicerone.create()

    @Singleton
    @Provides
    @Named("sub")
    fun getSubCicerone() = Cicerone.create()

    @Singleton
    @Provides
    @Named("main")
    fun getMainRouter(@Named("main") cicerone: Cicerone<Router>) = cicerone.router

    @Singleton
    @Provides
    @Named("main")
    fun getMainNavigatorHolder(@Named("main") cicerone: Cicerone<Router>) = cicerone.navigatorHolder

    @Singleton
    @Provides
    @Named("sub")
    fun getSubRouter(@Named("sub") cicerone: Cicerone<Router>) = cicerone.router

    @Singleton
    @Provides
    @Named("sub")
    fun getSubNavigatorHolder(@Named("sub") cicerone: Cicerone<Router>) = cicerone.navigatorHolder
}
