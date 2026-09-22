package com.example.scorebatapp.data.standingremote

import com.example.scorebatapp.data.remote.ApiReference.API_TOKEN
import com.example.scorebatapp.data.standing.Season
import com.example.scorebatapp.data.standing.SeasonModel
import com.example.scorebatapp.data.standingremote.ApiStandingReference.END_POINT
import com.example.scorebatapp.data.standingremote.ApiStandingReference.SEASON
import com.example.scorebatapp.data.standingremote.ApiStandingReference.STANDING_BASE_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStandingDetails {

    @GET(END_POINT)
    suspend fun getStandingList(
        @Query(SEASON) season: String
    ): Response<SeasonModel>

}