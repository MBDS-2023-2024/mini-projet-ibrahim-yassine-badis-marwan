package com.gmail.eamosse.idbdata.repository

import android.util.Log
import com.gmail.eamosse.idbdata.api.response.PopularMoviesResponse
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.data.Trailer
import com.gmail.eamosse.idbdata.datasources.LocalDataSource
import com.gmail.eamosse.idbdata.datasources.OnlineDataSource
import com.gmail.eamosse.idbdata.utils.Result
import javax.inject.Inject

/**
 * La classe permettant de gérer les données de l'application
 */
class MovieRepository @Inject internal constructor(
    private val local: LocalDataSource,
    private val online: OnlineDataSource
) {

    /**
     * Récupérer le token permettant de consommer les ressources sur le serveur
     * Le résultat du datasource est converti en instance d'objets publiques
     */
    suspend fun getToken(): Result<Token> {
        return when (val result = online.getToken()) {
            is Result.Succes -> {
                //save the response in the local database
                local.saveToken(result.data)
                //return the response
                Result.Succes(result.data)
            }
            is Result.Error -> result
        }
    }

    suspend fun getCategories(): Result<List<Category>> {
        return when(val result = online.getCategories()) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val categories = result.data.map {
                    it.toCategory()
                }
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }

    suspend fun getMoviesByCategoryId(id: Int): Result<List<Movie>> {
        return when(val result = online.getMoviesByCategoryId(id)) {
            is Result.Succes -> {
                val movies = result.data.map {
                    it.toMovie()
                }
                Result.Succes(movies)
            }
            is Result.Error -> result
        }
    }

    suspend fun getTrailerByMovieId(id: Int): Result<Trailer> {
        return when(val result = online.getTrailerByMovieId(id)) {
            is Result.Succes -> {
                val trailer = result.data.toMovieTrailer()
                Result.Succes(trailer)
            }
            is Result.Error -> result
        }
    }
    suspend fun getPopularMovies(): Result<List<Movie>> {
        return when (val result = online.getPopularMovies()) {
            is Result.Succes -> {
                val popularMovies = result.data.map { it.toMovie()
                }
                Result.Succes(popularMovies)
            }
            is Result.Error -> {
                result}
        }
    }

    suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return when (val result = online.getTopRatedMovies()) {
            is Result.Succes -> {
                val popularMovies = result.data.map { it.toMovie()
                }
                Result.Succes(popularMovies)
            }
            is Result.Error -> result
        }
    }

    suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return when (val result = online.getUpcomingMovies()) {
            is Result.Succes -> {
                val upcomingMovies = result.data.map { it.toMovie()
                }
                Result.Succes(upcomingMovies)
            }
            is Result.Error -> result
        }
    }

    suspend fun getMovies(id: Int): Result<Movie> {
        return when(val result = online.getMovies(id)) {
            is Result.Succes -> {
                Log.d("repo CCCCC", "Displaying movie title: ${result.data}")
                Result.Succes(result.data)
            }
            is Result.Error ->{
                Log.d("DDDDDDD ", "Displaying movie title: ")

                result
            }
        }
    }


}