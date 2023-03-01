package com.example.scorebatapp.data.remote

import com.example.scorebatapp.data.model.LeagueItemModel
import com.example.scorebatapp.data.remote.ApiReference
import com.example.scorebatapp.data.remote.ApiReference.API_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

    @GET(ApiReference.END_POINT)
    suspend fun getCompetitionList(
        @Query(API_TOKEN) apiDetails: String
    ):Response<LeagueItemModel>


}