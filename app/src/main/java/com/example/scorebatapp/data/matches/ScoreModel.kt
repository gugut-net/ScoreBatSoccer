package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class ScoreModel(
    @SerializedName("duration")
    val duration: String? = "",
    @SerializedName("fullTime")
    val fullTime: FullTimeModel? = FullTimeModel(),
    @SerializedName("halfTime")
    val halfTime: HalfTimeModel? = HalfTimeModel(),
    @SerializedName("winner")
    val winner: String? = ""
)