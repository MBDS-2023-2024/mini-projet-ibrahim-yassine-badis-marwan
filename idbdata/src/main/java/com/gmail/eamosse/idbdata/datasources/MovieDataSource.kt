package com.gmail.eamosse.idbdata.datasources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.utils.Result

interface MovieDataSource {



    suspend fun getToken(): Result<Token>
    suspend fun saveToken(token: Token)
    suspend fun getFavoriteMovies(): LiveData<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)

    suspend fun getFavoriteMovieById(id: Long): Movie?
}