package com.badmitry.bkshackaton.di.modules

import com.badmitry.domain.interactors.BondsInteractor
import com.badmitry.domain.repositories.IMoexRepositories
import com.badmitry.domain.repositories.INetworkChecker
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Module
class InteractorsModule {

    @Singleton
    @Provides
    fun vtbAuthInteractor(
        moexRepository: IMoexRepositories,
        networkChecker: INetworkChecker,
        @Named("IoScheduler") scheduler: Scheduler,
        @Named("MainScheduler") postScheduler: Scheduler
    ): BondsInteractor =
        BondsInteractor(moexRepository, networkChecker, scheduler, postScheduler)
}
