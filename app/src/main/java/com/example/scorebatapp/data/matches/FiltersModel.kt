package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class FiltersModel(
    @SerializedName("dateFrom")
    val dateFrom: String? = "",
    @SerializedName("dateTo")
    val dateTo: String? = "",
    @SerializedName("permission")
    val permission: String? = ""
)