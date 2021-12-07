package com.badmitry.data.helpers

import android.util.Log
import com.badmitry.domain.entities.BondData
import com.badmitry.domain.entities.InvestParams
import com.badmitry.domain.entities.ParamsOfPortfolio
import java.util.*
import kotlin.math.roundToInt

object Calculation {
    private val MINIMAL_COUNT_OF_BONDS = 1
    private val COUNT_OF_BONDS = 10
    private val COUNT_OF_GOS_BONDS = 3
    private val MINIMAL_SUMM = 12000
    private val finalList = mutableListOf<BondData>()
    private val MAX_SUM_FOR_IIS = 400000
    private val MAX_IIS = 52000

    fun calculatePortfolio(listBonds: List<BondData>, investParams: InvestParams): List<BondData> {
        finalList.clear()
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        investParams.investTime?.let {
            calendar.add(Calendar.YEAR, it)
        }
        val currentListOfBonds = mutableListOf<BondData>()
        listBonds.forEach { bond ->
            if (bond.dateOfClose.time < calendar.time.time && bond.couponValue != 0.0 && bond.profitabilityInPercent != 0.0) {
                (bond.nominalPrice * bond.priceInPercent * bond.sizeOfLot / 100).apply {
                    bond.priceOfLot = this
                    investParams.investSum?.let {
                        if (it > MINIMAL_SUMM) {
                            if (it > this * COUNT_OF_BONDS) {
                                currentListOfBonds.add(bond)
                            }
                        } else {
                            if (it > this) {
                                currentListOfBonds.add(bond)
                            }
                        }
                    }
                }
            }
        }
        currentListOfBonds.forEach {
            calendar.time = it.dateOfClose
            while (calendar.time.time > currentDate.time) {
                it.listOfDateCoupons.add(calendar.time)
                calendar.add(Calendar.DAY_OF_YEAR, -it.periodCouponPayouts)
            }
            var profitability = 0.0
            it.listOfDateCoupons.forEach { date ->
                profitability += it.couponValue
            }
            profitability += it.nominalPrice - it.nominalPrice * it.priceInPercent / 100
            profitability -= it.accumulatedCoupon
            it.profitabilityReal = profitability / (it.nominalPrice * it.priceInPercent)
//            Log.e(
//                "!!!evil",
//                "${it.name} $profitability ${it.profitabilityReal} ${it.listOfDateCoupons.size}"
//            )
        }
        currentListOfBonds.sortBy { it.profitabilityReal }
        investParams.investSum?.let {
            if (it < MINIMAL_SUMM) {
                calculateOptimalBond(currentListOfBonds, MINIMAL_COUNT_OF_BONDS)
            } else {
                val currentListOfGosBonds = mutableListOf<BondData>()
                currentListOfBonds.forEach {
                    if (it.isGos) {
                        currentListOfGosBonds.add(it)
                    }
                }
                if (currentListOfGosBonds.size > COUNT_OF_GOS_BONDS) {
                    calculateOptimalBond(currentListOfGosBonds, COUNT_OF_GOS_BONDS)
                    calculateOptimalBond(currentListOfBonds, COUNT_OF_BONDS - COUNT_OF_GOS_BONDS)
                } else {
                    calculateOptimalBond(currentListOfGosBonds, currentListOfGosBonds.size)
                    calculateOptimalBond(
                        currentListOfBonds,
                        COUNT_OF_BONDS - currentListOfGosBonds.size
                    )
                }
            }
        }
        return finalList
    }

    private fun calculateOptimalBond(listBonds: List<BondData>, countResult: Int) {
        for (i in (listBonds.size - 1) downTo (listBonds.size - countResult)) {
            finalList.add(listBonds[i])
            Log.e("!!!", "${listBonds[i].name} ${listBonds[i].profitabilityReal}")
        }
    }

    fun byBond(
        listBonds: List<BondData>,
        currency: String,
        investSum: Double,
        countOfYears: Int,
        useIIS: Boolean
    ): ParamsOfPortfolio {
        var cash = investSum
        var accumulatedCoupons = 0.0
        var maxPriceOfProportion =
            if (investSum > MINIMAL_SUMM) investSum / COUNT_OF_BONDS else investSum
        listBonds.forEach { bond ->
            val countOfLots =
                ((maxPriceOfProportion / (bond.priceOfLot + bond.accumulatedCoupon * bond.sizeOfLot)) / bond.sizeOfLot).toInt()
            val accumulatedCoupon = bond.accumulatedCoupon * bond.sizeOfLot * countOfLots
            accumulatedCoupons += accumulatedCoupon
            cash -= (countOfLots * bond.priceOfLot + accumulatedCoupon)
            bond.countOfBondInPortfolio = countOfLots / bond.sizeOfLot
            bond.priceOfBondsInPortfolio =
                (countOfLots * bond.priceOfLot / bond.sizeOfLot * 100).roundToInt() / 100.0
            bond.currency = currency
            bond.countOfBondInPortfolioInPercent =
                ((bond.priceOfBondsInPortfolio / investSum * 100) * 100).roundToInt() / 100.0
        }
        val paramsOfPortfolio =
            ParamsOfPortfolio(listBonds, currency, cash, investSum, paymentToAccumulatedCoupons = accumulatedCoupons, countOfYears = countOfYears)
        val calendar = Calendar.getInstance()
        listBonds.forEach { bond ->
            bond.listOfDateCoupons.forEach { date ->
                calendar.time = date
                val year = calendar.get(Calendar.YEAR)
                Log.e("!!!", "${year} ${bond.name}")
                var coupon = bond.couponValue * bond.countOfBondInPortfolio
                couponPayout(paramsOfPortfolio, year, coupon)
            }
            calendar.time = bond.dateOfClose
            val lastYear = calendar.get(Calendar.YEAR)
            val price = bond.nominalPrice * bond.countOfBondInPortfolio
            payout(paramsOfPortfolio, lastYear, price)
        }
        if (useIIS) {
            val currentDate = Date()
            calendar.time = currentDate
            for (i in 0 until (countOfYears - 1)) {
                val sumOfIIS = if (paramsOfPortfolio.inputSum > MAX_SUM_FOR_IIS) {
                    MAX_IIS.toDouble()
                } else {
                    paramsOfPortfolio.inputSum * 0.13
                }
                calendar.add(Calendar.YEAR, 1)
                val yearForIIS = calendar.get(Calendar.YEAR)
                paramsOfPortfolio.mapForIIS[yearForIIS] = sumOfIIS
                paramsOfPortfolio.sumOfIIS += sumOfIIS
            }
        }
        paramsOfPortfolio.cash += paramsOfPortfolio.sumOfIIS
        paramsOfPortfolio.cash = (paramsOfPortfolio.cash * 100).roundToInt() / 100.0
        paramsOfPortfolio.profitabilityValue =
            ((paramsOfPortfolio.cash - paramsOfPortfolio.inputSum) * 100).roundToInt() / 100.0
        paramsOfPortfolio.profitabilityInPercent =
            ((paramsOfPortfolio.profitabilityValue / paramsOfPortfolio.inputSum * 100) / paramsOfPortfolio.countOfYears * 100).roundToInt() / 100.0
        return paramsOfPortfolio
    }

    private fun couponPayout(paramsOfPortfolio: ParamsOfPortfolio, year: Int, payout: Double) {
        paramsOfPortfolio.mapOfCouponPayouts[year]?.let {
            paramsOfPortfolio.mapOfCouponPayouts[year] = (payout + it)
        } ?: paramsOfPortfolio.mapOfCouponPayouts.put(year, payout)
        paramsOfPortfolio.cash += payout
    }

    private fun payout(paramsOfPortfolio: ParamsOfPortfolio, year: Int, payout: Double) {
        paramsOfPortfolio.mapOfPayout[year]?.let {
            paramsOfPortfolio.mapOfPayout[year] = (payout + it)
        } ?: paramsOfPortfolio.mapOfPayout.put(year, payout)
        paramsOfPortfolio.cash += payout
    }
}