package com.gmail.eamosse.idbdata.datasources

import android.util.Log
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.idbdata.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface FirebaseDataSource {
    suspend fun insertFavoriteMovie(favoriteMovie: Movie): Result<Boolean>
    suspend fun getFavoriteMovies(): Result<List<Movie>>
    suspend fun getFavoriteMovieById(id: Long): Result<Movie>
    suspend fun deleteFavoriteMovie(id: Long): Result<Boolean>

    suspend fun insertFavoriteSeries(favoriteSeries: Serie): Result<Boolean>
    suspend fun getFavoriteSeries(): Result<List<Serie>>
    suspend fun getFavoriteSeriesById(id: Long): Result<Serie>
    suspend fun deleteFavoriteSeries(id: Long): Result<Boolean>
}
/**
 * Implementation of FirebaseDataSource for handling favorite movies.
 */
class FirebaseDataSourceImpl @Inject constructor() : FirebaseDataSource   {

    // Reference to the Firebase Realtime Database
    private val database = FirebaseDatabase.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override suspend fun insertFavoriteMovie(favoriteMovie: Movie): Result<Boolean> {

        // Assuming you have a "favorites" node in your Firebase database
        val favoritesRef = database.reference.child("My NextFlix")
            .child(currentUser?.uid ?: "")
            .child("favorites")
            .child("movies")
            .child(favoriteMovie.id.toString())

        // Convert Movie object to a Map for easier insertion
        val movieMap = mapOf(
            "id" to favoriteMovie.id,
            "adult" to favoriteMovie.adult,
            "backdropPath" to favoriteMovie.backdropPath,
            "title" to favoriteMovie.title,
            "originalLanguage" to favoriteMovie.originalLanguage,
            "originalTitle" to favoriteMovie.originalTitle,
            "overview" to favoriteMovie.overview,
            "posterPath" to favoriteMovie.posterPath,
            "mediaType" to favoriteMovie.mediaType,
            "popularity" to favoriteMovie.popularity,
            "releaseDate" to favoriteMovie.releaseDate,
            "video" to favoriteMovie.video,
            "voteAverage" to favoriteMovie.voteAverage,
            "voteCount" to favoriteMovie.voteCount
        )

        // Set the movie data in the Firebase Realtime Database
        favoritesRef.setValue(movieMap).await()

        return Result.Succes(true)

    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return try {
            // Assuming you have a "favorites" node in your Firebase database
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("movies")

            val snapshot = favoritesRef.get().await()

            if (snapshot.exists()) {
                val moviesList = snapshot.children.map { data ->
                    // Convert the data to a Movie object
                    val movieData = data.getValue(Movie::class.java)
                    movieData ?: throw Exception("Failed to parse movie data")
                }

                Result.Succes(moviesList)
            } else {
                Result.Succes(emptyList())
            }

        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }

    override suspend fun getFavoriteMovieById(id: Long): Result<Movie> {
        return try {
            var movie: Movie?
            // Assuming you have a "favorites" node in your Firebase database
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("movies")
                .child(id.toString())

            val snapshot = favoritesRef.get().await()

            if (snapshot.exists()) {
                // Convert the data to a Movie object
                val movieData = snapshot.getValue(Movie::class.java)
                movieData?.let {
                    Result.Succes(it)
                } ?: Result.Error(Exception("Failed to parse movie data"), code = 500, message = "Internal Server Error")
            } else {
                Result.Error(Exception("Movie not found in favorites"), code = 404, message = "Movie not found")
            }

        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }

    override suspend fun deleteFavoriteMovie(id: Long): Result<Boolean> {
        return try {
            // Assuming you have a "favorites" node in your Firebase database
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("movies")
                .child(id.toString())

            favoritesRef.removeValue().await()

            Result.Succes(true)
        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }

    override suspend fun insertFavoriteSeries(favoriteSeries: Serie): Result<Boolean> {
        val favoritesRef = database.reference.child("My NextFlix")
            .child(currentUser?.uid ?: "")
            .child("favorites")
            .child("series")
            .child(favoriteSeries.id.toString())

        val seriesMap = mapOf(
            "id" to favoriteSeries.id,
            "backdropPath" to favoriteSeries.backdropPath,
            "firstAirDate" to favoriteSeries.firstAirDate,
            "name" to favoriteSeries.name,
            "originCountry" to favoriteSeries.originCountry,
            "originalLanguage" to favoriteSeries.originalLanguage,
            "originalName" to favoriteSeries.originalName,
            "overview" to favoriteSeries.overview,
            "popularity" to favoriteSeries.popularity,
            "posterPath" to favoriteSeries.posterPath,
            "voteAverage" to favoriteSeries.voteAverage,
            "voteCount" to favoriteSeries.voteCount
        )


        favoritesRef.setValue(seriesMap).await()

        return Result.Succes(true)
    }

    override suspend fun getFavoriteSeries(): Result<List<Serie>> {
        return try {
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("series")

            val snapshot = favoritesRef.get().await()

            if (snapshot.exists()) {
                val seriesList = snapshot.children.map { data ->
                    val seriesData = data.getValue(Serie::class.java)
                    seriesData ?: throw Exception("Failed to parse series data")
                }

                Result.Succes(seriesList)
            } else {
                Result.Succes(emptyList())
            }

        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }

    override suspend fun getFavoriteSeriesById(id: Long): Result<Serie> {
        return try {
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("series")
                .child(id.toString())

            val snapshot = favoritesRef.get().await()

            if (snapshot.exists()) {

                val seriesData = snapshot.getValue(Serie::class.java)

                seriesData?.let {
                    Result.Succes(it)
                } ?: Result.Error(Exception("Failed to parse series data"), code = 500, message = "Internal Server Error")
            } else {
                Result.Error(Exception("Series not found in favorites"), code = 404, message = "Series not found")
            }

        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }

    override suspend fun deleteFavoriteSeries(id: Long): Result<Boolean> {
        return try {
            val favoritesRef = database.reference.child("My NextFlix")
                .child(currentUser?.uid ?: "")
                .child("favorites")
                .child("series")
                .child(id.toString())

            favoritesRef.removeValue().await()

            Result.Succes(true)
        } catch (e: Exception) {
            Result.Error(e, code = 500, message = "Internal Server Error")
        }
    }
}
