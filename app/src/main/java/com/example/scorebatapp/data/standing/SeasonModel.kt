package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class SeasonModel(
    @SerializedName("data")
    val season: Season = Season(),
    @SerializedName("status")
    val status: Boolean? = false
)