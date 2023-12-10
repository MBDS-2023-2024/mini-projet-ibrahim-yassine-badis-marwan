package com.gmail.eamosse.idbdata.repository

import android.util.Log

import com.gmail.eamosse.idbdata.data.Category

import com.gmail.eamosse.idbdata.data.MovieProviderPackage.CountryResult

import com.gmail.eamosse.idbdata.data.PopularPerson
import com.gmail.eamosse.idbdata.data.RatingBody
import com.gmail.eamosse.idbdata.data.Review
import com.gmail.eamosse.idbdata.data.Serie

import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.data.Trailer
import com.gmail.eamosse.idbdata.datasources.FirebaseDataSourceImpl
import com.gmail.eamosse.idbdata.datasources.LocalDataSource
import com.gmail.eamosse.idbdata.datasources.OnlineDataSource
import com.gmail.eamosse.idbdata.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.sql.Types.NULL
import javax.inject.Inject
import com.gmail.eamosse.idbdata.data.Movie as Movie

/**
 * La classe permettant de gérer les données de l'application
 */
class MovieRepository @Inject internal constructor(
    private val local: LocalDataSource,
    private val online: OnlineDataSource,
    private val firebase: FirebaseDataSourceImpl
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

    suspend fun getSeriesByCategoryId(id: Int): Result<List<Serie>> {
        return when(val result = online.getSeriesByCategoryId(id)) {
            is Result.Succes -> {
                val movies = result.data.map {
                    it.toSerie()
                }
                Result.Succes(movies)
            }
            is Result.Error -> result
        }
    }
    suspend fun getPopularMovies(): Result<List<Movie>> {
        return when (val result = online.getPopularMovies()) {
            is Result.Succes -> {
                val popularMovies = result.data.map { it.toMoviePop()
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
                val popularMovies = result.data.map { it.toMoviePop()
                }
                Result.Succes(popularMovies)
            }
            is Result.Error -> result
        }
    }

    suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return when (val result = online.getUpcomingMovies()) {
            is Result.Succes -> {
                val upcomingMovies = result.data.map { it.toMoviePop()
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


    suspend fun getTrailerByMovieId(id: Int): Result<Trailer> {
        return when(val result = online.getTrailerByMovieId(id)) {
            is Result.Succes -> {
                val trailer = result.data.toTrailer()
                Result.Succes(trailer)
            }
            is Result.Error -> result
        }
    }

    suspend fun getTrailerBySeriesId(id: Int): Result<Trailer> {
        return when(val result = online.getTrailerBySeriesId(id)) {
            is Result.Succes -> {
                val trailer = result.data.toTrailer()
                Result.Succes(trailer)
            }
            is Result.Error -> result
        }
    }


    suspend fun getProvidersByMovieId(id: Int): Result<Collection<CountryResult>> {
        return when(val result = online.getProvidersByMovieId(id)) {
            is Result.Succes -> {
                val providers = result.data.map {
                    it.toCountryResult()
                }
                Result.Succes(providers)
            }


            is Result.Error -> result
        }
    }

    suspend fun getProvidersBySerieId(id: Int): Result<Collection<CountryResult>> {
        return when(val result = online.getProvidersBySerieId(id)) {
            is Result.Succes -> {
                val providers = result.data.map {
                    it.toCountryResult()
                }
                Result.Succes(providers)
            }


            is Result.Error -> result
        }
    }

    suspend fun addRating(id: Int, rating: RatingBody): Result<Boolean> {
        return when(val result = online.addRating(id,rating)) {
            is Result.Succes -> {
                val ratingResponse = result.data
                Result.Succes(ratingResponse)

            }
            is Result.Error -> result
        }
    }


    /******Firebase******/


    suspend fun getFavoriteMoviesFromFirebase(): List<Movie> {
        Log.i("getfavorite", "je suis dans repository")
        return when (val result = firebase.getFavoriteMovies()) {
            is Result.Succes -> result.data
            is Result.Error -> {
                // Handle the error, log it, show a toast, etc.
                Log.e("MovieRepository", "Error getting favorite movies: ${result.exception}")
                emptyList()
            }
        }
    }
    suspend fun getFavoriteByIdMoviesFromFirebase(id: Long): Movie? {
        Log.i("getfavorite", "je suis dans repository")
        return when (val result = firebase.getFavoriteMovieById(id)) {
            is Result.Succes -> result.data
            is Result.Error -> {
                // Handle the error, log it, show a toast, etc.
                Log.e("MovieRepository", "Error getting favorite movies: ${result.exception}")
                val nothing = null
                nothing

            }
        }
    }

    suspend fun getFavoriteSerieFromFirebase(): List<Serie> {
        Log.i("getfavorite", "je suis dans repository")
        return when (val result = firebase.getFavoriteSeries()) {

            is Result.Succes ->{
                Log.i("awawaaw", "je suis dans repository${result}")

                result.data}
            is Result.Error -> {
                // Handle the error, log it, show a toast, etc.
                Log.e("MovieRepository", "Error getting favorite movies: ${result.exception}")
                emptyList()
            }
        }
    }
    suspend fun getFavoriteByIdSerieFromFirebase(id: Long): Serie? {
        Log.i("getfavorite", "je suis dans repository")
        return when (val result = firebase.getFavoriteSeriesById(id)) {
            is Result.Succes -> result.data
            is Result.Error -> {
                // Handle the error, log it, show a toast, etc.
                Log.e("MovieRepository", "Error getting favorite movies: ${result.exception}")
                val nothing = null
                nothing

            }
        }
    }

    suspend fun  insertFavoriteMoviesFirebase(fav : Movie){
        firebase.insertFavoriteMovie(fav)

    }
    suspend fun  getFavoriteMoviesFirebase(): List<Movie>{
        return getFavoriteMoviesFromFirebase()
    }
    suspend fun  deleteFavoriteMoviesFirebase(fav : Movie){
        firebase.deleteFavoriteMovie(fav.id)
    }

    suspend fun  getFavoriteMoviesFirebaseById(id : Long):Movie?{
        return getFavoriteByIdMoviesFromFirebase(id)
    }

    suspend fun  insertFavoriteSeriesFirebase(fav : Serie){
        firebase.insertFavoriteSeries(fav)


    }
    suspend fun  getFavoriteSeriesFirebase(): List<Serie>{
        return getFavoriteSerieFromFirebase()
    }
    suspend fun  deleteFavoriteSeriesFirebase(fav : Serie){
        firebase.deleteFavoriteSeries(fav.id)
    }

    suspend fun  getFavoriteSeriesFirebaseById(id : Long):Serie?{
        return getFavoriteByIdSerieFromFirebase(id)
    }




    suspend fun getListAllPopularPersons(): Result<List<PopularPerson>> {
        return when(val result = online.getPopularPersons()) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val popularPersons = result.data.map {
                    it.toPopularPerson()
                }
                Result.Succes(popularPersons)
            }
            is Result.Error -> result
        }
    }

    suspend fun getReviewsByMovieId(id: Int): Result<List<Review>> {
        return when(val result = online.getReviewsByMovieId(id)) {
            is Result.Succes -> {
                val reviews = result.data.map {
                    it.toReviewResult()
                }
                Result.Succes(reviews)
            }
            is Result.Error -> result
        }
    }

    suspend fun getReviewsBySeriesId(id: Int): Result<List<Review>> {
        return when(val result = online.getReviewsBySeriesId(id)) {
            is Result.Succes -> {
                val reviews = result.data.map {
                    it.toReviewResult()
                }
                Result.Succes(reviews)
            }
            is Result.Error -> result
        }
    }

}