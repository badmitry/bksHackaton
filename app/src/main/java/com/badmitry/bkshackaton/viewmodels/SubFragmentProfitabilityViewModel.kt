package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.SaverParams
import com.badmitry.domain.entities.Finance
import java.util.*
import javax.inject.Inject

class SubFragmentProfitabilityViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {
    val liveData = MutableLiveData<List<Finance>>()

    fun saveCurrentScreen(finances: List<Finance>) {
        liveData.value = finances
    }

    fun getFinance(finances: List<Finance>) {
        val currentFinances = mutableListOf<Finance>()
        SaverParams.instance.paramsOfPortfolio?.let {
            val currentDate = Date()
            val calendar = Calendar.getInstance()
            calendar.time = currentDate
            var sumOfIIS = 0.0
            var sumBonds = 0.0
            it.bonds.forEach {
                sumBonds += it.priceOfBondsInPortfolio
            }
            var cash = it.inputSum - sumBonds - it.paymentToAccumulatedCoupons
            for (i in 0 until (it.countOfYears + 1)) {
                val year = calendar.get(Calendar.YEAR)
                it.bonds.forEach {
                    val thisCalendar = Calendar.getInstance()
                    thisCalendar.time = it.dateOfClose
                    thisCalendar.get(Calendar.YEAR)
                    if (thisCalendar.get(Calendar.YEAR) == year) {
                        sumBonds -= it.priceOfBondsInPortfolio
                        cash += it.nominalPrice * it.countOfBondInPortfolio
                    }
                }
                it.mapOfCouponPayouts.forEach {
                    if (it.key == year) {
                        cash += it.value
                    }
                }
                it.mapForIIS.forEach {
                    if (it.key == year) {
                        sumOfIIS += it.value
                    }
                }
                currentFinances.add(
                    Finance(
                        finances[1].colorRes,
                        cash.toInt(),
                        finances[1].subscription,
                        year
                    )
                )
                if (it.sumOfIIS != 0.0) {
                    currentFinances.add(
                        Finance(
                            finances[2].colorRes,
                            (sumOfIIS).toInt(),
                            finances[2].subscription,
                            year
                        )
                    )
                }
                currentFinances.add(
                    Finance(
                        finances[0].colorRes,
                        sumBonds.toInt(),
                        finances[0].subscription,
                        year
                    )
                )
                calendar.add(Calendar.YEAR, 1)
            }
        }
        liveData.value = currentFinances
    }
}