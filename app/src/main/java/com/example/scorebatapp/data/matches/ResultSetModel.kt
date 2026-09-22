package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class ResultSetModel(
    @SerializedName("competitions")
    val competitions: String? = "",
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("first")
    val first: String? = "",
    @SerializedName("last")
    val last: String? = "",
    @SerializedName("played")
    val played: Int? = 0
)