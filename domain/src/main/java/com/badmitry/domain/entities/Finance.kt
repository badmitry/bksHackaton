package com.badmitry.domain.entities

data class Finance(
    val colorRes: Int,
    var value: Int,
    val subscription: String? = null,
    var year: Int
)
