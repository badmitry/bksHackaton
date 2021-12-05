package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.badmitry.bkshackaton.navigation.FragmentScreensProvider
import com.badmitry.bkshackaton.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class MainFragmentViewModel @Inject constructor(
    @param:Named("main") private val router: Router,
    app: Application
) : BaseViewModel(app) {
    val liveData = MutableLiveData<Int>()

    fun navigateTo(screen: Screens) {
        router.navigateTo(FragmentScreensProvider(screen))
    }
}