package com.badmitry.domain.entities

data class ParamsOfPortfolio(
    val bonds: List<BondData>,
    val currency: String,
    var cash: Double,
    var inputSum: Double,
    var profitabilityInPercent: Double = 0.0,
    var profitabilityValue: Double = 0.0,
    val mapOfPayout: MutableMap<Int, Double> = mutableMapOf(),
    val mapOfCouponPayouts: MutableMap<Int, Double> = mutableMapOf(),
    val mapForIIS: MutableMap<Int, Double> = mutableMapOf(),
    val paymentToAccumulatedCoupons: Double = 0.0,
    var sumOfIIS: Double = 0.0,
    var countOfYears: Int
)


