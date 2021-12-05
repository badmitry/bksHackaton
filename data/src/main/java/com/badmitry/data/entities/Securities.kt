package com.badmitry.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Securities(
    @Expose @SerializedName("data") val data: List<List<String>>
) : Serializable