package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class RefereeModel(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("nationality")
    val nationality: String? = "",
    @SerializedName("type")
    val type: String? = ""
)