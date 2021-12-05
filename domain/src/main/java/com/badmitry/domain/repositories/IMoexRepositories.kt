package com.badmitry.domain.repositories

import com.badmitry.domain.entities.BondData
import io.reactivex.Single

interface IMoexRepositories {
    fun getRUBonds(): Single<List<BondData>>

    fun getRUCorporateBonds(): Single<List<BondData>>

    fun getUSDBonds(): Single<List<BondData>>
}