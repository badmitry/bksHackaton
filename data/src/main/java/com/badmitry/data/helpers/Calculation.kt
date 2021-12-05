package com.badmitry.data.helpers

import android.util.Log
import com.badmitry.domain.entities.BondData
import com.badmitry.domain.entities.InvestParams
import com.badmitry.domain.entities.ParamsOfPortfolio
import java.util.*
import java.util.concurrent.TimeUnit

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
            val countOfDays = TimeUnit.DAYS.convert(
                (it.dateOfClose.time - currentDate.time),
                TimeUnit.MILLISECONDS
            )
//            Log.e("!!!", "${it.name} $countOfDays")
            it.profitabilityReal =
                it.profitabilityInPercent * it.priceOfLot!! / 100 / it.sizeOfLot / 365 * countOfDays / (it.nominalPrice * it.priceInPercent / 100)
//            Log.e("!!!", " ${it.profitabilityReal}")
            calendar.time = it.dateOfClose
            while (calendar.time.time > currentDate.time) {
                it.listOfDateCoupons.add(calendar.time)
                calendar.add(Calendar.DAY_OF_YEAR, -it.periodCouponPayouts)
            }
//            it.listOfDateCoupons.forEach {
//                Log.e("!!!", it.toString())
//            }
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
        Log.e("!!!", "calculateOptimalBond ${listBonds.size - 1} ${listBonds.size - countResult}")
        for (i in (listBonds.size - 1) downTo (listBonds.size - countResult)) {
            finalList.add(listBonds[i])
            Log.e("!!!", "${listBonds[i].name} ${listBonds[i].profitabilityReal}")
        }
    }

    fun byBond(
        listBonds: List<BondData>,
        currency: String,
        investSum: Double,
        years: Int,
        useIIS: Boolean
    ): ParamsOfPortfolio {
        var cash = 0.0
        var maxPriceOfProportion = 0.0
        cash = investSum
        maxPriceOfProportion =
            if (investSum > MINIMAL_SUMM) investSum / COUNT_OF_BONDS else investSum
        listBonds.forEach { bond ->
            var count = 0
            bond.priceOfLot?.let {
                count =
                    ((maxPriceOfProportion / (it + bond.accumulatedCoupon * bond.sizeOfLot)) / bond.sizeOfLot).toInt()
                cash -= (count * it + bond.accumulatedCoupon * bond.sizeOfLot * count)
                Log.e("cash:", cash.toString())
                bond.countOfBondInPortfolio = count
            }
        }
        val paramsOfPortfolio = ParamsOfPortfolio(listBonds, currency, cash, investSum, years = years)
        val calendar = Calendar.getInstance()
        listBonds.forEach { bond ->
            bond.listOfDateCoupons.forEach { date ->
                calendar.time = date
                val year = calendar.get(Calendar.YEAR)
                Log.e("!!!", "${year} ${bond.name}")
                var coupon = 0.0
                bond.countOfBondInPortfolio?.let {
                    coupon = bond.couponValue * it
                } ?: run {
                    coupon = bond.couponValue * 1
                }
                payout(paramsOfPortfolio, year, coupon)
            }
            calendar.time = bond.dateOfClose
            val lastYear = calendar.get(Calendar.YEAR)
            var price = 0.0
            bond.countOfBondInPortfolio?.let {
                price = bond.nominalPrice * it
            } ?: run {
                price = bond.nominalPrice * 1
            }
            payout(paramsOfPortfolio, lastYear, price)
        }
        if (useIIS) {
            val currentDate = Date()
            calendar.time = currentDate
            var yearForIIS = calendar.get(Calendar.YEAR)
            for (i in 0 until (years + 1)) {
                val sumOfIIS = if (paramsOfPortfolio.inputSum > MAX_SUM_FOR_IIS) {
                    MAX_IIS.toDouble()
                } else {
                    paramsOfPortfolio.inputSum * 0.13
                }
                calendar.add(Calendar.YEAR, 1)
                yearForIIS = calendar.get(Calendar.YEAR)
                paramsOfPortfolio.mapForIIS[yearForIIS] = sumOfIIS
                paramsOfPortfolio.mapForIIS.forEach{
                    Log.e("evil:::", "${it.key} ${it.value}")
                }
                paramsOfPortfolio.sumOfIIS += sumOfIIS
            }
        }
        Log.e("evil:::", "${paramsOfPortfolio.cash} ${paramsOfPortfolio.sumOfIIS} ${paramsOfPortfolio.inputSum}")
        paramsOfPortfolio.cash += paramsOfPortfolio.sumOfIIS
        paramsOfPortfolio.profitabilityValue =
            (paramsOfPortfolio.cash - paramsOfPortfolio.inputSum).toInt()
        paramsOfPortfolio.profitabilityInPercent =
            ((paramsOfPortfolio.profitabilityValue / paramsOfPortfolio.inputSum * 100) / years).toInt()
        return paramsOfPortfolio
    }

    private fun payout(paramsOfPortfolio: ParamsOfPortfolio, year: Int, payout: Double) {
        paramsOfPortfolio.mapOfPayouts[year]?.let {
            paramsOfPortfolio.mapOfPayouts += year to (payout + it)
        } ?: paramsOfPortfolio.mapOfPayouts.put(year, payout)
        paramsOfPortfolio.cash += payout
    }
}