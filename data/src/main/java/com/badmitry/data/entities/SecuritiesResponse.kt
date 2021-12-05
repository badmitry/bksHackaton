package com.badmitry.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SecuritiesResponse(
    @Expose @SerializedName("securities") val securities: Securities
) : Serializable