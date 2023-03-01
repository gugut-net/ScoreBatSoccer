package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class CompetitionModel(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("emblem")
    val emblem: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("type")
    val type: String? = ""
)