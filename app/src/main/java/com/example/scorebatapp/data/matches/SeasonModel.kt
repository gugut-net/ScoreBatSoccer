package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class SeasonModel(
    @SerializedName("currentMatchday")
    val currentMatchday: Int? = 0,
    @SerializedName("endDate")
    val endDate: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("startDate")
    val startDate: String? = "",
//    @SerializedName("winner")
//    val winner: AnyModel? = AnyModel()
)