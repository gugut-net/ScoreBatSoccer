package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class OddsModel(
    @SerializedName("awayWin")
    val awayWin: Double? = 0.0,
    @SerializedName("draw")
    val draw: Double? = 0.0,
    @SerializedName("homeWin")
    val homeWin: Double? = 0.0
)