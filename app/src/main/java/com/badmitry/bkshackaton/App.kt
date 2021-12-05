package com.badmitry.bkshackaton

import com.badmitry.bkshackaton.di.DaggerAppComponent
import dagger.android.DaggerApplication


class App : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build()
}