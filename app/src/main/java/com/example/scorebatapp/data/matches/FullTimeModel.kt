package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class FullTimeModel(
    @SerializedName("away")
    val away: Int? = 0,
    @SerializedName("home")
    val home: Int? = 0
)