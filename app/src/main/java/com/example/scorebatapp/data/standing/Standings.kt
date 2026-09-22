package com.example.scorebatapp.data.standing


import com.google.gson.annotations.SerializedName

data class Standings(
    @SerializedName("note")
    val note: NoteModel? = NoteModel(),
    @SerializedName("stats")
    val stats: List<StatModel> = listOf(),
    @SerializedName("team")
    val team: TeamModel = TeamModel()
)