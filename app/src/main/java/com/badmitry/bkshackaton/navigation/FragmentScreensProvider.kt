package com.badmitry.bkshackaton.navigation

import androidx.fragment.app.Fragment
import com.badmitry.bkshackaton.fragments.FragmentMain
import com.badmitry.bkshackaton.fragments.FragmentOptions
import com.badmitry.bkshackaton.fragments.FragmentPortfolio
import com.badmitry.bkshackaton.fragments.FragmentPortfolioGraph
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentPayouts
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentPortfolio
import com.badmitry.bkshackaton.fragments.subfragments.SubFragmentProfitability
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FragmentScreensProvider(private val screen: Screens) : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return when (screen) {
            Screens.MAIN -> FragmentMain()
            Screens.OPTIONS -> FragmentOptions()
            Screens.PORTFOLIO_GRAPH -> FragmentPortfolioGraph()
            Screens.PORTFOLIO -> FragmentPortfolio()
            Screens.PAYOUTS -> SubFragmentPayouts()
            Screens.PORTFOLIO_SUB -> SubFragmentPortfolio()
            Screens.PROFITABILITY -> SubFragmentProfitability()
        }
    }
}