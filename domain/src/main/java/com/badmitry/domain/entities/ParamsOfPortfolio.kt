package com.badmitry.domain.entities

data class ParamsOfPortfolio(
    val bonds: List<BondData>,
    val currency: String,
    var cash: Double = 0.0,
    var inputSum: Double = 0.0,
    var profitabilityInPercent: Int = 0,
    var profitabilityValue: Int = 0,
    val mapOfPayouts: MutableMap<Int, Double> = mutableMapOf(),
    val mapForIIS: MutableMap<Int, Double> = mutableMapOf(),
    var sumOfIIS: Double = 0.0,
    var years: Int
)


