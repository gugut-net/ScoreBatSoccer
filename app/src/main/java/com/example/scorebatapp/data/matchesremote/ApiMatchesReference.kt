package com.example.scorebatapp.data.matchesremote

import com.example.scorebatapp.BuildConfig

object ApiMatchesReference {

    //https://api.football-data.org/v4/matches

    const val MATCHES_BASE_URL = "https://api.football-data.org/v4/matches/"
    const val MATCHES_END_POINT = "/v4/competitions/2021/matches"
    //const val MATCHES_SEASON = "/v4/matches/2021"
    const val MATCHES_API_TOKEN = "API_TOKEN"

    val AUTH_TOKEN: String
        get() = BuildConfig.FOOTBALL_DATA_API_TOKEN
    const val AUTH_HEADER = "X-Auth-Token"
}
