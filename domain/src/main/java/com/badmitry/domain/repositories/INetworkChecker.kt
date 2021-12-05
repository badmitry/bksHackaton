package com.badmitry.domain.repositories

import io.reactivex.Single

interface INetworkChecker {
    fun isConnect(): Single<Boolean>
}