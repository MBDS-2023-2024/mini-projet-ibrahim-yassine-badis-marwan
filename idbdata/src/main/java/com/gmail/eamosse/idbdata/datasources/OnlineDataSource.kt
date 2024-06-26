package com.gmail.eamosse.idbdata.datasources

import ReviewResponse
import android.util.Log
import com.gmail.eamosse.idbdata.api.response.CategoryResponse
import com.gmail.eamosse.idbdata.api.response.MovieResponse


import com.gmail.eamosse.idbdata.api.response.WatchProvidersResponse

import com.gmail.eamosse.idbdata.api.response.PopularMoviesResponse
import com.gmail.eamosse.idbdata.api.response.TrailerResponse
import com.gmail.eamosse.idbdata.api.response.PopularPersonResponse
import com.gmail.eamosse.idbdata.api.response.SerieResponse

import com.gmail.eamosse.idbdata.api.service.MovieService
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.api.response.toToken
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.RatingBody
import com.gmail.eamosse.idbdata.data.Serie
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

    override suspend fun saveToken(token: Token) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovieById(id: Long): Movie? {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteSeries(): List<Serie> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteSeries(serie: Serie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteSeries(series: Serie) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteSeriesById(id: Long): Serie? {
        TODO("Not yet implemented")
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

    suspend fun getSeriesByCategoryId(id: Int): Result<List<SerieResponse.Item>> {
        return safeCall {
            when (val response = service.getListSeriesById(id).parse()) {
                is Result.Succes -> {
                    Result.Succes(response.data.items)
                }
                is Result.Error -> response
            }
        }
    }

    suspend fun getTrailerByMovieId(id: Int): Result<TrailerResponse.Result> {
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


    suspend fun getProvidersByMovieId(id: Int): Result<Collection<WatchProvidersResponse.CountryResult>> {
        return safeCall {
            when (val response = service.getProvidersByMovieId(id).parse()) {
                is Result.Succes -> {
                    Result.Succes(response.data.results.values)
                }

                is Result.Error -> {
                    response
                }
            }
        }
    }

    suspend fun getProvidersBySerieId(id: Int): Result<Collection<WatchProvidersResponse.CountryResult>> {
        return safeCall {
            when (val response = service.getProvidersBySerieId(id).parse()) {
                is Result.Succes -> {
                    Result.Succes(response.data.results.values)
                }

                is Result.Error -> {
                    response
                }
            }
        }
    }

        suspend fun getTrailerBySeriesId(id: Int): Result<TrailerResponse.Result> {
            return safeCall {
                when (val response = service.getTrailerBySeriesId(id).parse()) {
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
                when (val response = service.addRating(id, rating.toRatingBodyEntity()).parse()) {
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

        suspend fun getPopularPersons(): Result<List<PopularPersonResponse.Item>> {
            return safeCall {
                when (val response = service.getPopularPersons().parse()) {
                    is Result.Succes -> {
                        Result.Succes(response.data.results)
                    }

                    is Result.Error -> response
                }
            }
        }


        suspend fun getPopularMovies(): Result<List<PopularMoviesResponse.Items>> {
            return safeCall {
                when (val response = service.getPopularMovies().parse()) {
                    is Result.Succes -> {
                        Log.d("online data source 1212121212121221", "Number of Popular Movies:")
                        Result.Succes(response.data.results)
                    }

                    is Result.Error -> {
                        Log.d("online data source 33333333333333333", "Number of Popular Movies: ")

                        response
                    }
                }
            }
        }


        suspend fun getTopRatedMovies(): Result<List<PopularMoviesResponse.Items>> {
            return safeCall {
                when (val response = service.getTopRatedMovies().parse()) {
                    is Result.Succes -> {
                        Result.Succes(response.data.results)
                    }

                    is Result.Error -> response
                }
            }
        }

        suspend fun getUpcomingMovies(): Result<List<PopularMoviesResponse.Items>> {
            return safeCall {
                when (val response = service.getUpcomingMovies().parse()) {
                    is Result.Succes -> {
                        Result.Succes(response.data.results)
                    }

                    is Result.Error -> response
                }
            }
        }

        suspend fun getMovies(id: Int): Result<Movie> {
            return safeCall {
                when (val response = service.getMovieById(id.toLong()).parse()) {
                    is Result.Succes -> {
                        Log.d("AAAAA", "Displaying movie titles: ${response.data}")
                        Result.Succes(response.data.toMovie())
                    }

                    is Result.Error -> {
                        Log.d("BBBBBBBBBBBB", "Displaying movie titles: ")

                        response
                    }
                }
            }
        }


    suspend fun getReviewsByMovieId(id: Int): Result<List<ReviewResponse.ReviewEntity>> {
        return safeCall {
            when (val response = service.getReviewsByMovieId(id).parse()) {
                is Result.Succes -> {
                    /**
                     * On recupere une liste des videos et le trailer se trouve à la fin de la liste
                     */
                    Result.Succes(response.data.reviews)
                }
                is Result.Error -> response
            }
        }
    }

    suspend fun getReviewsBySeriesId(id: Int): Result<List<ReviewResponse.ReviewEntity>> {
        return safeCall {
            when (val response = service.getReviewsBySeriesId(id).parse()) {
                is Result.Succes -> {
                    /**
                     * On recupere une liste des videos et le trailer se trouve à la fin de la liste
                     */
                    Result.Succes(response.data.reviews)
                }
                is Result.Error -> response
            }
        }
    }

}

