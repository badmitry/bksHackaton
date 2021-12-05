package com.badmitry.bkshackaton

import com.badmitry.domain.entities.ParamsOfPortfolio

class SaverParams {
    var paramsOfPortfolio: ParamsOfPortfolio? = null

    companion object {
        val instance = SaverParams()
    }
}