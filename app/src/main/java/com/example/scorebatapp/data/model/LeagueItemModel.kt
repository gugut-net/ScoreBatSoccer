package com.example.scorebatapp.data.model


import com.google.gson.annotations.SerializedName

data class LeagueItemModel(
    @SerializedName("response")
    val response: List<ResponseItemModel>? = listOf()
)