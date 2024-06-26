package com.gmail.eamosse.idbdata.datasources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.utils.Result

interface MovieDataSource {



    suspend fun getToken(): Result<Token>
    suspend fun saveToken(token: Token)
    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)

    suspend fun getFavoriteMovieById(id: Long): Movie?

    suspend fun getFavoriteSeries(): List<Serie>

    suspend fun insertFavoriteSeries(serie: Serie)

    suspend fun deleteFavoriteSeries(series: Serie)

    suspend fun getFavoriteSeriesById(id: Long): Serie?
}