package com.badmitry.bkshackaton.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Provides
    @Named("IoScheduler")
    fun ioScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("MainScheduler")
    fun mainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
