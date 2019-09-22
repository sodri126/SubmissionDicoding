package com.example.submission4.data.api

import com.example.submission4.BuildConfig
import com.example.submission4.data.api.model.*
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FilmService {
    @GET("discover/movie")
    suspend fun getListMovies(@Query("api_key") api: String, @Query("page") page: Int = 1, @Query("with_original_language") withOriginalLanguage: String = "en"): Response<DiscoverResponse<DiscoverMovie>>

    @GET("discover/tv")
    suspend fun getListTvShow(@Query("api_key") api: String, @Query("page") page: Int = 1, @Query("with_original_language") withOriginalLanguage: String = "en"): Response<DiscoverResponse<DiscoverTv>>

    @GET("movie/{id_movie}")
    suspend fun getDetailMovie(@Path("id_movie") idMovie: Int, @Query("api_key") api: String): Response<DetailMovie>

    @GET("tv/{id_tv}")
    suspend fun getDetailTvShow(@Path("id_tv") idTv: Int, @Query("api_key") api: String): Response<DetailTv>
}

object FilmServiceFactory {
    @Volatile
    private var INSTANCE: FilmService? = null
    private const val readTimeOut = 10L
    private const val connectTimeOut = 10L

    fun settingOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    fun getService(okHttpClient: OkHttpClient, gson: Gson): FilmService {
        return INSTANCE ?: synchronized(this) {
            val instance = Retrofit.Builder()
            .baseUrl("${BuildConfig.SERVER_URL}/${BuildConfig.API_VERSION}/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(FilmService::class.java)
            INSTANCE = instance
            instance
        }
    }

}