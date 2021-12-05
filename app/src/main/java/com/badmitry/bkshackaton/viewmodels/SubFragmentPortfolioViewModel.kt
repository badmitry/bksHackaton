package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.SaverParams
import com.badmitry.domain.entities.Bond
import javax.inject.Inject

class SubFragmentPortfolioViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {
    val liveData = MutableLiveData<List<Bond>>()

//    fun saveCurrentScreen(list: List<Bond>) {
//        liveData.value = list
//    }

    fun getBonds(listOfBonds: MutableList<Bond>) {
        SaverParams.instance.paramsOfPortfolio?.let {
            var fullSum = 0.0
            var sumOfGos = 0.0
            var sumOfCorp = 0.0
            var sumOfCash = 0.0
            it.bonds.forEach {
                val sum = (it.countOfBondInPortfolio!! * it.nominalPrice * it.priceInPercent)
                fullSum += sum
                if (it.isGos) {
                    sumOfGos += sum
                } else {
                    sumOfCorp += sum
                }
            }
            sumOfCash = it.cash
            listOfBonds[0].perсentOfWeight = (sumOfGos / fullSum * 100).toInt()
            listOfBonds[1].perсentOfWeight = (sumOfCorp / fullSum * 100).toInt()
            listOfBonds[2].perсentOfWeight = (sumOfCash / fullSum * 100).toInt()
            liveData.value = listOfBonds
        }
    }
}