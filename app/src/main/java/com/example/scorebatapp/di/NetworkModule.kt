package com.example.scorebatapp.di


import com.example.scorebatapp.data.matchesremote.ApiMatchesDetails
import com.example.scorebatapp.data.remote.ApiDetails
import com.example.scorebatapp.data.remote.ApiReference
import com.example.scorebatapp.data.repository.Repository
import com.example.scorebatapp.data.repository.RepositoryImpl
import com.example.scorebatapp.data.standingremote.ApiStandingDetails
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
class NetworkModule {

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Named("league")
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiReference.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiDetails(
        @Named("league") retrofit: Retrofit
    ): ApiDetails = retrofit.create(ApiDetails::class.java)

    @Provides
    fun provideRepository(
        apiDetails: ApiDetails,
        apiStandingDetails: ApiStandingDetails,
        apiMatchesNetworkModule: ApiMatchesDetails
    ): Repository =
        RepositoryImpl(apiDetails, apiStandingDetails, apiMatchesNetworkModule)
}