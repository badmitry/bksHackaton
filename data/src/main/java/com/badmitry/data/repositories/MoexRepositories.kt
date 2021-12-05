package com.badmitry.data.repositories

import com.badmitry.data.api.MoexApi
import com.badmitry.data.mapers.BondMaper
import com.badmitry.domain.entities.BondData
import com.badmitry.domain.repositories.IMoexRepositories
import io.reactivex.Single

class MoexRepositories(private val api: MoexApi) : IMoexRepositories {
    override fun getRUBonds(): Single<List<BondData>> {
        return api.getRUGosBonds().map { BondMaper.getBondFromResponse(it) }
    }

    override fun getRUCorporateBonds(): Single<List<BondData>> {
        return api.getRUCorporateBonds().map { BondMaper.getBondFromResponse(it) }
    }

    override fun getUSDBonds(): Single<List<BondData>> {
        return api.getUSDBonds().map { BondMaper.getBondFromResponse(it) }
    }
}