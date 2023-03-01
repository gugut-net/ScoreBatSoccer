package com.example.scorebatapp.data.repository

import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.data.model.LeagueItemModel
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.util.ResponseType
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getCompetitionList(): Response<LeagueItemModel>

    suspend fun getStandingList(): Flow<ResponseType<List<Standings>>>

    suspend fun getMatchesList(): Response<MatchesModel>
}