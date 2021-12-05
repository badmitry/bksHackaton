package com.badmitry.bkshackaton.view

import com.badmitry.domain.entities.EventArgs

interface OnErrorView {
    fun onError(arg: EventArgs<Throwable>)
}