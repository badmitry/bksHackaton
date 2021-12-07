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
    val dateOfCloseInString: String,
    val periodCouponPayouts: Int,
    var isGos: Boolean = false,
    var priceOfLot: Double = 0.0,
    var profitabilityReal: Double = 0.0,
    val listOfDateCoupons: MutableList<Date> = mutableListOf(),
    var countOfBondInPortfolio: Int = 0,
    var countOfBondInPortfolioInPercent: Double = 0.0,
    var priceOfBondsInPortfolio: Double = 0.0,
    var currency: String = Currencys.RUB.name
)


