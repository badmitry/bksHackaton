package com.badmitry.domain.interactors

import com.badmitry.domain.entities.BondData
import com.badmitry.domain.entities.Currencys
import com.badmitry.domain.exceptions.InternetConnectionException
import com.badmitry.domain.repositories.IMoexRepositories
import com.badmitry.domain.repositories.INetworkChecker
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class BondsInteractor @Inject constructor(
    private val repositories: IMoexRepositories,
    private val networkChecker: INetworkChecker,
    @Named("IoScheduler") scheduler: Scheduler,
    @Named("MainScheduler") postScheduler: Scheduler
) : BaseInteractor<List<BondData>, BondsInteractor.Params>(scheduler, postScheduler) {
    data class Params(val currency: Currencys)

    override fun createSingle(params: Params): Single<List<BondData>> {
        return networkChecker.isConnect().flatMap { it ->
            if (it) {
                val listOfBonds = mutableListOf<BondData>()
                if (params.currency == Currencys.RUB) {
                    return@flatMap repositories.getRUBonds().flatMap { listBonds ->
                        listBonds.forEach { it.isGos = true }
                        listOfBonds.addAll(listBonds)
                        repositories.getRUCorporateBonds().map { listCorporateBonds ->
                            listOfBonds.addAll(listCorporateBonds)
                            return@map listOfBonds
                        }
                    }
                } else {
                    return@flatMap repositories.getUSDBonds()
                }
            } else {
                return@flatMap Single.create<List<BondData>> { emitter ->
                    emitter.onError(InternetConnectionException())
                }
            }
        }
    }
}