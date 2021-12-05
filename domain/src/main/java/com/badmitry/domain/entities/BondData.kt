package com.badmitry.domain.entities

import java.util.*

data class BondData(
    val name: String,
    val priceInPercent: Double,
    val profitabilityInPercent: Double,
    val couponValue: Double,
    val dateOfNextCoupon: Date,
    val accumulatedCoupon: Double,
//    val currentPriceInPercent: Double,
    val sizeOfLot: Int,
    val nominalPrice: Double,
    val dateOfClose: Date,
    val periodCouponPayouts: Int,
    var isGos: Boolean = false,
    var priceOfLot: Double? = null,
    var profitabilityReal: Double? = null,
    val listOfDateCoupons: MutableList<Date> = mutableListOf(),
    var countOfBondInPortfolio: Int? = null
)


