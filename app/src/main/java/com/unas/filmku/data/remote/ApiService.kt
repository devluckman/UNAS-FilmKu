package com.unas.filmku.data.remote

import com.unas.filmku.BuildConfig
import com.unas.filmku.data.response.NowPlayingResponse
import com.unas.filmku.data.response.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {


    @GET("3/movie/popular")
    suspend fun getNowPlayingMovie(
        @Header("Authorization") token : String
    ) : NowPlayingResponse

    @GET("3/movie/now_playing")
    suspend fun getPopularMovie(
        @Header("Authorization") token : String
    ) : PopularResponse



}