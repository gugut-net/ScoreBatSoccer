package com.example.scorebatapp.data.repository

import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.data.matchesremote.ApiMatchesDetails
import com.example.scorebatapp.data.matchesremote.ApiMatchesReference
import com.example.scorebatapp.data.matchesremote.ApiMatchesReference.AUTH_TOKEN
import com.example.scorebatapp.data.model.LeagueItemModel
import com.example.scorebatapp.data.remote.ApiDetails
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.data.standingremote.ApiStandingDetails
import com.example.scorebatapp.di.ApiMatchesNetworkModule
import com.example.scorebatapp.util.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(

    val apiDetails: ApiDetails,
    val standingDetails: ApiStandingDetails,
    val matchesDetails: ApiMatchesDetails

) : Repository {

    override suspend fun getCompetitionList(): Response<LeagueItemModel> {
        return apiDetails.getCompetitionList("NjAyNDdfMTY3NzE3NTI4NV8wZDQ4Y2RjMTgzNWUxM2MwYTc5ZDdiMGUwYzM5ZWExNTM4YjdkNjdi")
    }

    override suspend fun getMatchesList(): Response<MatchesResponseModel> {
        return matchesDetails.getMatchesList(AUTH_TOKEN)
    }

    override suspend fun getStandingList(): Flow<ResponseType<List<Standings>>> = flow  {
        emit(ResponseType.Loading)

        try {
            val response = standingDetails.getStandingList("2022")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseType.Success(it.season.standings))
                }
            }else {
                emit(ResponseType.Error(response.message()))
            }
        }catch (e: java.lang.Exception) {
            emit(ResponseType.Error(e.localizedMessage))
        }
    }

}