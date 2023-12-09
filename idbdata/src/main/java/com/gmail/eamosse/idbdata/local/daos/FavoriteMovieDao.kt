package com.gmail.eamosse.idbdata.local.daos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity


@Dao
internal interface FavoriteMovieDao {

    @Insert
    fun insert(entity: FavoriteMovieEntity)

    @Query("SELECT * from idb_favoriteMovies")
    fun retrieve(): List<FavoriteMovieEntity>

    @Delete
    fun delete(entity: FavoriteMovieEntity)

    @Query("SELECT * FROM idb_favoriteMovies WHERE id = :id")
    fun getFavoriteMovieById(id: Long): FavoriteMovieEntity?
}