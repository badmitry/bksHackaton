package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.navigation.FragmentScreensProvider
import com.badmitry.bkshackaton.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class PortfolioGraphViewModel @Inject constructor(
    @param:Named("sub") private val subRouter: Router,
    @param:Named("sub") private val navigatorHolder: NavigatorHolder,
    @param:Named("main") private val mainRouter: Router,
    app: Application
) : BaseViewModel(app) {
    val liveData = MutableLiveData<Int>()

    fun setNavigator(navigator: SupportAppNavigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun navigateToSubScreen(screen: Screens) {
        subRouter.replaceScreen(FragmentScreensProvider(screen))
    }

    fun navigateToMainScreen(screen: Screens) {
        mainRouter.replaceScreen(FragmentScreensProvider(screen))
    }

    fun saveCurrentScreen(subScreenNumber: Int) {
        liveData.value = subScreenNumber
    }

    override fun onCleared() {
        super.onCleared()
        navigatorHolder.removeNavigator()
    }
}