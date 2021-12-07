package com.badmitry.data.mapers

import android.annotation.SuppressLint
import com.badmitry.data.entities.SecuritiesResponse
import com.badmitry.domain.entities.BondData
import java.text.SimpleDateFormat
import java.util.*

object BondMaper {
    fun getBondFromResponse(securitiesResponse: SecuritiesResponse): List<BondData> {
        val listOfBonds = mutableListOf<BondData>()
        securitiesResponse.securities.data.forEach {
            if (it[2] != null && it[3] != null && it[4] != null && it[5] != null && it[7] != null && it[9] != null && it[10] != null && it[15] != null) {
                listOfBonds.add(
                    BondData(
                        it[2],
                        it[3].toDouble(),
                        it[4].toDouble(),
                        it[5].toDouble(),
                        convertToDate(it[6]),
                        it[7].toDouble(),
                        it[9].toInt(),
                        it[10].toDouble(),
                        convertToDate(it[13]),
                        it[13],
                        it[15].toInt()
                    )
                )
            }
        }
        return listOfBonds
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertToDate(date: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(date)
    }
}