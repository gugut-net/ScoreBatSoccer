package com.example.scorebatapp.di

import com.example.scorebatapp.data.remote.ApiDetails
import com.example.scorebatapp.data.remote.ApiReference
import com.example.scorebatapp.data.repository.Repository
import com.example.scorebatapp.data.repository.RepositoryImpl
import com.example.scorebatapp.data.standingremote.ApiStandingDetails
import com.example.scorebatapp.data.standingremote.ApiStandingReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class StandingNetworkModule {

    @Provides
    @Named("standing")
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiStandingReference.STANDING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiDetails(
        @Named("standing")retrofit: Retrofit
    ): ApiStandingDetails = retrofit.create(ApiStandingDetails::class.java)

}