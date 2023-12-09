package com.gmail.eamosse.idbdata.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.local.daos.FavoriteMovieDao
import com.gmail.eamosse.idbdata.local.daos.FavoriteSeriesDao
import com.gmail.eamosse.idbdata.local.daos.TokenDao
import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity
import com.gmail.eamosse.idbdata.local.entities.FavoriteSeriesEntity
import com.gmail.eamosse.idbdata.local.entities.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import com.gmail.eamosse.idbdata.utils.Result

@Singleton
internal class LocalDataSource @Inject constructor(private val tokenDao: TokenDao, private val favoriteMovieDao: FavoriteMovieDao,
                                                   private val favoriteSeriesDao: FavoriteSeriesDao
) :
    MovieDataSource {

    override suspend fun getToken(): Result<Token> = withContext(Dispatchers.IO) {
        tokenDao.retrieve()?.let {
            Result.Succes(it.toToken())
        } ?: Result.Error(
            exception = Exception(),
            message = "You should get a token from the API first",
            code = -1
        )
    }

    override suspend fun saveToken(token: Token) {
        withContext(Dispatchers.IO) {
            tokenDao.insert(token.toEntity())
        }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        // Récupération de la liste des entités FavoriteMovie depuis la base de données
        val favoriteMovieEntities = favoriteMovieDao.retrieve()

        // Transformation de chaque FavoriteMovieEntity en Movie
        val listFavoriteMovies = favoriteMovieEntities.map { entity ->
            entity.toFavoriteMovie()
        }

        return listFavoriteMovies
    }

    /*Transformations.map(favoriteMovieDao.retrieve()) { entities ->
            entities.map { entity ->
                entity.toFavoriteMovie()
            }
        }*/


    override suspend fun insertFavoriteMovie(movie: Movie) {
        Log.i("favorite", "je suis dans LocalDataSource")
        val movieEntity = movie.toFavoriteMovieEntity()
        withContext(Dispatchers.IO) {
            favoriteMovieDao.insert(movieEntity);
        }
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        val movieEntity = movie.toFavoriteMovieEntity()
        withContext(Dispatchers.IO) {
            favoriteMovieDao.delete(movieEntity);
        }
    }

    override suspend fun getFavoriteMovieById(id: Long): Movie? {
        var movie: Movie?
        withContext(Dispatchers.IO) {
            movie = favoriteMovieDao.getFavoriteMovieById(id)?.toFavoriteMovie();
        }
        return movie
    }

    override suspend fun getFavoriteSeries(): List<Serie> {
        // Récupération de la liste des entités FavoriteMovie depuis la base de données
        val favoriteSeriesEntities = favoriteSeriesDao.retrieve()

        // Transformation de chaque FavoriteMovieEntity en Movie
        val listFavoriteSeries = favoriteSeriesEntities.map { entity ->
            entity.toFavoriteSerie()
        }

        return listFavoriteSeries
    }

    override suspend fun insertFavoriteSeries(serie: Serie) {
        Log.i("favorite", "je suis dans LocalDataSource")
        val seriesEntity = serie.toFavoriteSeriesEntity()
        withContext(Dispatchers.IO) {
            favoriteSeriesDao.insert(seriesEntity);
        }
    }

    override suspend fun deleteFavoriteSeries(series: Serie) {
        val seriesEntity = series.toFavoriteSeriesEntity()
        withContext(Dispatchers.IO) {
            favoriteSeriesDao.delete(seriesEntity);
        }
    }

    override suspend fun getFavoriteSeriesById(id: Long): Serie? {
        var series: Serie?
        withContext(Dispatchers.IO) {
            series = favoriteSeriesDao.getFavoriteSeriesById(id)?.toFavoriteSerie();
        }
        return series
    }


}
internal fun Token.toEntity() = TokenEntity(
    expiresAt = this.expiresAt,
    token = this.requestToken
)

internal fun TokenEntity.toToken() = Token(
    expiresAt = this.expiresAt,
    requestToken = this.token
)

internal fun Movie.toFavoriteMovieEntity() = FavoriteMovieEntity(
    adult = this.adult,
    backdropPath = this.backdropPath,
    id =  this.id,
    title =  this.title.toString(),
    originalLanguage = this.originalLanguage.toString(),
    originalTitle =  this.originalTitle.toString(),
    overview =  this.overview.toString(),
    posterPath = this.posterPath.toString() ,
    mediaType =  this.mediaType.toString(),
    popularity = this.popularity,
    releaseDate = this.releaseDate.toString(),
    video =  this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
)


internal fun FavoriteMovieEntity.toFavoriteMovie() = Movie(
    adult = this.adult,
    backdropPath = this.backdropPath,
    id =  this.id,
    title =  this.title,
    originalLanguage = this.originalLanguage,
    originalTitle =  this.originalTitle,
    overview =  this.overview,
    posterPath = this.posterPath,
    mediaType =  this.mediaType,
    popularity = this.popularity,
    releaseDate = this.releaseDate,
    video =  this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
)

internal fun Serie.toFavoriteSeriesEntity() = FavoriteSeriesEntity(
     firstAirDate =  this.firstAirDate,
    originCountry = this.originCountry,
    backdropPath = this.backdropPath,
    id =  this.id,
    originalName =  this.originalName,
    originalLanguage = this.originalLanguage.toString(),
    overview =  this.overview.toString(),
    posterPath = this.posterPath.toString() ,
    popularity = this.popularity,
    voteCount = this.voteCount,
    name = this.name,
    voteAverage = this.voteAverage
)


internal fun FavoriteSeriesEntity.toFavoriteSerie() = Serie(
    firstAirDate =  this.firstAirDate,
    originCountry = this.originCountry,
    backdropPath = this.backdropPath,
    id =  this.id,
    originalName =  this.originalName,
    originalLanguage = this.originalLanguage.toString(),
    overview =  this.overview.toString(),
    posterPath = this.posterPath.toString() ,
    popularity = this.popularity,
    voteCount = this.voteCount,
    name = this.name,
    voteAverage = this.voteAverage
)