package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class StatModel(
    @SerializedName("abbreviation")
    val abbreviation: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("displayName")
    val displayName: String? = "",
    @SerializedName("displayValue")
    val displayValue: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("shortDisplayName")
    val shortDisplayName: String? = "",
    @SerializedName("summary")
    val summary: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("value")
    val value: Int? = 0
)