package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class MatchesResponseModel(
    @SerializedName("filters")
    val filters: FiltersModel? = FiltersModel(),
    @SerializedName("matches")
    val matches: List<MatchesModel>? = listOf(),
    @SerializedName("resultSet")
    val resultSet: ResultSetModel? = ResultSetModel()
)