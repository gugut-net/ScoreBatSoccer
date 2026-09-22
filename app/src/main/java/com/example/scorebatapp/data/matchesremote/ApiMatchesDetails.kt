package com.example.scorebatapp.data.matchesremote

import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.data.matchesremote.ApiMatchesReference.MATCHES_API_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMatchesDetails {

    @GET(ApiMatchesReference.MATCHES_END_POINT)
    suspend fun getMatchesList(
        @Query(MATCHES_API_TOKEN) apiMatchesDetails: String
    ): Response<MatchesResponseModel>

}