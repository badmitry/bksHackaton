package com.badmitry.domain.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InvestParams(
    @Expose @SerializedName("invest_sum") var investSum: Double? = null,
    @Expose @SerializedName("invest_currency") var investCurrency: String? = null,
    @Expose @SerializedName("invest_currency_value") var investCurrencyValue: String? = null,
    @Expose @SerializedName("invest_time") var investTime: Int? = null,
    @Expose @SerializedName("invest_year") var investYear: String? = null,
    @Expose @SerializedName("reinvest") var reinvest: Boolean = false,
    @Expose @SerializedName("use_iis") var useIis: Boolean = false,
    @Expose @SerializedName("regular_replenishment") var regularReplenishment: Boolean = false,
    @Expose @SerializedName("sum_replenishment") var sumReplenishment: Int? = null
) : Serializable
