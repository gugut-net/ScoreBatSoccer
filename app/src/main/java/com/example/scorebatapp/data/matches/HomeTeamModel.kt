package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class HomeTeamModel(
    @SerializedName("crest")
    val crest: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shortName")
    val shortName: String? = "",
    @SerializedName("tla")
    val tla: String? = ""
)