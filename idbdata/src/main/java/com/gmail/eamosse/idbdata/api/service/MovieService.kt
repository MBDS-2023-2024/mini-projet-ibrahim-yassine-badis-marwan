package com.gmail.eamosse.idbdata.api.service

import com.gmail.eamosse.idbdata.api.response.CategoryResponse
import com.gmail.eamosse.idbdata.api.response.MovieResponse
import com.gmail.eamosse.idbdata.api.response.MovieTrailerResponse
import com.gmail.eamosse.idbdata.api.response.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MovieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("list/{id}")
    suspend fun getListMoviesById(@Path("id") id: Int): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerByMovieId(@Path("movie_id") id: Int): Response<MovieTrailerResponse>

    //chaines  movie/38/watch/providers
    // video   movie/movie_id/videos?language=en-US'
    // add Rating movie/{movie_id}/rating  https://developer.themoviedb.org/reference/movie-add-rating
}