package com.badmitry.data.api

import com.badmitry.data.entities.SecuritiesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface MoexApi {
    @GET("/iss/engines/stock/markets/bonds/boards/TQOB/securities.json")
    fun getRUGosBonds(): Single<SecuritiesResponse>

    @GET("/iss/engines/stock/markets/bonds/boards/TQCB/securities.json")
    fun getRUCorporateBonds(): Single<SecuritiesResponse>

    @GET("/iss/engines/stock/markets/bonds/boards/TQOD/securities.json")
    fun getUSDBonds(): Single<SecuritiesResponse>
}