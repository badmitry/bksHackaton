package com.badmitry.data.helpers

import com.badmitry.domain.entities.Currencys
import com.badmitry.domain.entities.InvestParams
import com.badmitry.domain.entities.ValidationErrors

object Validator {
    private val MINIMAL_SUMM = 1100
    private val finalList = mutableListOf<ValidationErrors>()

    fun validate(investParams: InvestParams): List<ValidationErrors> {
        finalList.clear()
        investParams.investSum?.let {
            if (it < MINIMAL_SUMM) {
                finalList.add(ValidationErrors.INVEST_SUM)
            }
        } ?: finalList.add(ValidationErrors.INVEST_SUM)
        investParams.investCurrencyValue ?: finalList.add(ValidationErrors.INVEST_CURRENCY)
        investParams.investTime ?: finalList.add(ValidationErrors.INVEST_TIME)
        if (investParams.regularReplenishment) {
            investParams.sumReplenishment ?: finalList.add(ValidationErrors.SUM_REPLENISHMENT)
        }
        if (investParams.useIis) {
            investParams.investTime?.let {
                if (it < 3) {
                    finalList.add(ValidationErrors.USE_IIS)
                }
            }
            investParams.investCurrencyValue?.let{
                if(it != Currencys.RUB.name) {
                    finalList.add(ValidationErrors.INVEST_CURRENCY)
                }
            }
        }
        return finalList
    }
}