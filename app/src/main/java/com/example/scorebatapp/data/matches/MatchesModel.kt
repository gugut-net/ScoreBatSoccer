package com.example.scorebatapp.data.matches


import com.google.gson.annotations.SerializedName

data class MatchesModel(
    @SerializedName("area")
    val area: AreaModel? = AreaModel(),
    @SerializedName("awayTeam")
    val awayTeam: AwayTeamModel? = AwayTeamModel(),
    @SerializedName("competition")
    val competition: CompetitionModel? = CompetitionModel(),
//    @SerializedName("group")
//    val group: AnyModel? = AnyModel(),
    @SerializedName("homeTeam")
    val homeTeam: HomeTeamModel? = HomeTeamModel(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("lastUpdated")
    val lastUpdated: String? = "",
    @SerializedName("matchday")
    val matchday: Int? = 0,
    @SerializedName("odds")
    val odds: OddsModel? = OddsModel(),
    @SerializedName("referees")
    val referees: List<RefereeModel?>? = listOf(),
    @SerializedName("score")
    val score: ScoreModel? = ScoreModel(),
    @SerializedName("season")
    val season: SeasonModel? = SeasonModel(),
    @SerializedName("stage")
    val stage: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("utcDate")
    val utcDate: String? = ""
)