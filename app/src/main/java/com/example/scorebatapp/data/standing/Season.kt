package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("abbreviation")
    val abbreviation: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("season")
    val season: Int? = 0,
    @SerializedName("seasonDisplay")
    val seasonDisplay: String? = "",
    @SerializedName("standings")
    val standings: List<Standings> = listOf()
)