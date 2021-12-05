package com.badmitry.bkshackaton.view

import com.badmitry.domain.entities.EventArgs

interface OnProgressView {
    fun onProgress(arg: EventArgs<Boolean>)
}