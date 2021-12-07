package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.SaverParams
import com.badmitry.domain.entities.Bond
import javax.inject.Inject
import kotlin.math.roundToInt

class SubFragmentPortfolioViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {
    val liveData = MutableLiveData<List<Bond>>()

    fun getBonds(listOfBonds: MutableList<Bond>) {
        SaverParams.instance.paramsOfPortfolio?.let { params ->
            var sumOfGos = 0.0
            var sumOfCorp = 0.0
            params.bonds.forEach {
                val sum = it.priceOfBondsInPortfolio
                if (it.isGos) {
                    sumOfGos += sum
                } else {
                    sumOfCorp += sum
                }
            }
            val fullSum = params.inputSum
            val sumOfCash = params.inputSum - sumOfCorp - sumOfGos
            listOfBonds[0].perсentOfWeight = (sumOfGos / fullSum * 100).roundToInt()
            listOfBonds[1].perсentOfWeight = (sumOfCorp / fullSum * 100).roundToInt()
            listOfBonds[2].perсentOfWeight = (sumOfCash / fullSum * 100).roundToInt()
            liveData.value = listOfBonds
        }
    }
}