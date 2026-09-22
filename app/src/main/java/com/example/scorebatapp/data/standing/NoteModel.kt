package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class NoteModel(
    @SerializedName("color")
    val color: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("rank")
    val rank: Int? = 0
)