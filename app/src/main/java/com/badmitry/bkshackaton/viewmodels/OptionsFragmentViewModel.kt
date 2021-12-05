package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.navigation.FragmentScreensProvider
import com.badmitry.bkshackaton.navigation.Screens
import com.badmitry.data.helpers.Calculation
import com.badmitry.data.helpers.Validator
import com.badmitry.domain.entities.*
import com.badmitry.domain.interactors.BondsInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class OptionsFragmentViewModel @Inject constructor(
    app: Application,
    @param:Named("main") private val mainRouter: Router,
    val interactor: BondsInteractor
) : BaseViewModel(app) {
    private val investParams = InvestParams()

    val liveData = MutableLiveData<InvestParams>()
    val errorValidationLiveData = MutableLiveData<List<ValidationErrors>>()
    val paramsOfPortfolioLiveData = MutableLiveData<ParamsOfPortfolio>()

    fun setInvestParams(
        investingSum: String? = null,
        investCurrency: String? = null,
        investCurrencyValue: String? = null,
        investTime: String? = null,
        investYear: String? = null,
        reinvest: Boolean? = null,
        useIis: Boolean? = null,
        regularReplenishment: Boolean? = null,
        sumReplenishment: String? = null
    ) {
        errorValidationLiveData.value = listOf<ValidationErrors>()
        investingSum?.let {
            try {
                investParams.investSum = Integer.parseInt(it).toDouble()
            } catch (e: Exception) {
                investParams.investSum = null
            }
        }
        investCurrency?.let {
            investParams.investCurrency = it
        }
        investCurrencyValue?.let {
            investParams.investCurrencyValue = it
        }
        investTime?.let {
            try {
                investParams.investTime = Integer.parseInt(it)
            } catch (e: Exception) {
                investParams.investTime = null
            }
        }
        investYear?.let {
            investParams.investYear = it
        }
        reinvest?.let {
            investParams.reinvest = it
        }
        useIis?.let {
            investParams.useIis = it
        }
        regularReplenishment?.let {
            investParams.regularReplenishment = it
        }
        sumReplenishment?.let {
            try {
                investParams.sumReplenishment = Integer.parseInt(it)
            } catch (e: Exception) {
                investParams.sumReplenishment = null
            }
        }
        liveData.value = investParams
    }

    fun getBonds() {
        liveData.value?.let {
            val listOfErrors = Validator.validate(it)
            if (listOfErrors.isEmpty()) {
                val params =
                    if (it.investCurrencyValue == Currencys.RUB.name) BondsInteractor.Params(
                        Currencys.RUB
                    ) else BondsInteractor.Params(
                        Currencys.DOL
                    )
                interactor(params, ::onSubscribe, ::onFinally, ::onGetBond, ::onError)
            } else errorValidationLiveData.value = listOfErrors
        } ?: run {
            errorValidationLiveData.value = mutableListOf<ValidationErrors>(
                ValidationErrors.SUM_REPLENISHMENT,
                ValidationErrors.INVEST_CURRENCY,
                ValidationErrors.INVEST_TIME,
                ValidationErrors.INVEST_SUM,
                ValidationErrors.USE_IIS
            )
        }
    }

    fun navigate(screen: Screens) {
        mainRouter.navigateTo(FragmentScreensProvider(screen))
    }

    private fun onGetBond(bondDataList: List<BondData>) {
        liveData.value?.let {
            val buysBond = Calculation.calculatePortfolio(bondDataList, it)
            if (it.investCurrencyValue != null && it.investSum != null) {
                val paramsOfPortfolio =
                    Calculation.byBond(
                        buysBond,
                        it.investCurrencyValue!!,
                        it.investSum!!,
                        it.investTime!!,
                        it.useIis
                    )
                paramsOfPortfolioLiveData.value = paramsOfPortfolio
            }
        }
    }
}