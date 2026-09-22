package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class TeamModel(
    @SerializedName("abbreviation")
    val abbreviation: String? = "",
    @SerializedName("displayName")
    val displayName: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("isActive")
    val isActive: Boolean? = false,
    @SerializedName("isNational")
    val isNational: Boolean? = false,
    @SerializedName("location")
    val location: String? = "",
    @SerializedName("logos")
    val logos: List<LogoModel> = listOf(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shortDisplayName")
    val shortDisplayName: String? = "",
    @SerializedName("uid")
    val uid: String? = ""
)