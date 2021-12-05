package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.navigation.FragmentScreensProvider
import com.badmitry.bkshackaton.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(
    @param:Named("main") private val router: Router,
    @param:Named("main") private val navigatorHolder: NavigatorHolder,
    app: Application
) : BaseViewModel(app) {
    fun setNavigator(navigator: SupportAppNavigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun replaceFragment(screen: Screens) {
        router.replaceScreen(FragmentScreensProvider(screen))
    }

    override fun onCleared() {
        super.onCleared()
        navigatorHolder.removeNavigator()
    }

    val liveData = MutableLiveData<Int>()
}