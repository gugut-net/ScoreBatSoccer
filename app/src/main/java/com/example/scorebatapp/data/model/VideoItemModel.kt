package com.example.scorebatapp.data.model


import com.google.gson.annotations.SerializedName

data class VideoItemModel(
    @SerializedName("embed")
    val embed: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("title")
    val title: String? = ""
)