package com.example.scorebatapp.data.remote

import com.example.scorebatapp.BuildConfig

object ApiReference {

    //https://www.scorebat.com/video-api/v3/feed/?token=[YOUR_API_TOKEN]
    //https://www.scorebat.com/video-api/v3/competition/england-premier-league/?token=[YOUR_API_TOKEN]
    //https://www.scorebat.com/video-api/v3/team/real-madrid/?token=[YOUR_API_TOKEN]


    const val BASE_URL = "https://www.scorebat.com/"
    const val END_POINT = "video-api/v3/feed/"
    const val API_TOKEN = "token"
    val TOKEN: String
        get() = BuildConfig.SCOREBAT_API_TOKEN
}
