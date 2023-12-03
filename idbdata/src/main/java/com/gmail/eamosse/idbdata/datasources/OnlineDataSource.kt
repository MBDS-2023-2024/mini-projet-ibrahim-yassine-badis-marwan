package com.gmail.eamosse.idbdata.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gmail.eamosse.idbdata.api.request.RatingBody
import com.gmail.eamosse.idbdata.api.response.CategoryResponse
import com.gmail.eamosse.idbdata.api.response.MovieResponse
import com.gmail.eamosse.idbdata.api.response.MovieTrailerResponse
import com.gmail.eamosse.idbdata.api.response.RatingResponse
import com.gmail.eamosse.idbdata.api.service.MovieService
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.api.response.toToken
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Rating
import com.gmail.eamosse.idbdata.parse
import com.gmail.eamosse.idbdata.safeCall
import com.gmail.eamosse.idbdata.utils.Result
import javax.inject.Inject

/**
 * Manipule les données de l'application en utilisant un web service
 * Cette classe est interne au module, ne peut être initialisé ou exposé aux autres composants
 * de l'application
 */
internal class OnlineDataSource @Inject constructor(private val service: MovieService) :
    MovieDataSource {

    /**
     * Récupérer le token du serveur
     * @return [Result<Token>]
     * Si [Result.Succes], tout s'est bien passé
     * Sinon, une erreur est survenue
     */

    override suspend fun getToken(): Result<Token> {
        return safeCall {
            val response = service.getToken().parse()
            when (response) {
                is Result.Succes -> {
                    Result.Succes(response.data.toToken())
                }
                is Result.Error -> response
            }
        }
    }

    /*
    suspend fun getCategories(): Result<List<CategoryResponse.Genre>> {
        return try {
            val response = service.getCategories()
            if (response.isSuccessful) {
                Result.Succes(response.body()!!.genres)
            } else {
                Result.Error(
                    exception = Exception(),
                    message = response.message(),
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            Result.Error(
                exception = e,
                message = e.message ?: "No message",
                code = -1
            )
        }
    }
     */

    suspend fun getCategories(): Result<List<CategoryResponse.Genre>> {
        return safeCall {
            when (val response = service.getCategories().parse()) {
                is Result.Succes -> {
                    Result.Succes(response.data.genres)
                }
                is Result.Error -> response
            }
        }
    }


    suspend fun getMoviesByCategoryId(id: Int): Result<List<MovieResponse.Item>> {
        return safeCall {
            when (val response = service.getListMoviesById(id).parse()) {
                is Result.Succes -> {
                    Result.Succes(response.data.items)
                }
                is Result.Error -> response
            }
        }
    }

    suspend fun getTrailerByMovieId(id: Int): Result<MovieTrailerResponse.Result> {
        return safeCall {
            when (val response = service.getTrailerByMovieId(id).parse()) {
                is Result.Succes -> {
                    /**
                     * On recupere une liste des videos et le trailer se trouve à la fin de la liste
                      */
                    Result.Succes(response.data.results.last())
                }
                is Result.Error -> response
            }
        }
    }

    suspend fun addRating(id: Int, rating: RatingBody): Result<Boolean> {
        return safeCall {
            when (val response = service.addRating(id, rating).parse()) {
                is Result.Succes -> {
                    /**
                     * On recupere une liste des videos et le trailer se trouve à la fin de la liste
                     */
                    Result.Succes(response.data.success)
                }
                is Result.Error -> response
            }
        }
    }

    override suspend fun saveToken(token: Token) {
        TODO("I don't know how to save a token, the local datasource probably does")
    }

    override suspend fun getFavoriteMovies(): LiveData<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }
}

