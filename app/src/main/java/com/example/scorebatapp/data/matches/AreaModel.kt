package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class AreaModel(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("flag")
    val flag: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = ""
)