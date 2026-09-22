package com.example.scorebatapp.di

import com.example.scorebatapp.data.matchesremote.ApiMatchesDetails
import com.example.scorebatapp.data.matchesremote.ApiMatchesReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiMatchesNetworkModule {

    @Provides
    @Named("matches")
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiMatchesReference.MATCHES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiDetails(
        @Named("matches")retrofit: Retrofit
    ): ApiMatchesDetails = retrofit.create(ApiMatchesDetails::class.java)
}