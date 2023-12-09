package com.gmail.eamosse.idbdata.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity
import com.gmail.eamosse.idbdata.local.entities.FavoriteSeriesEntity
@Dao
interface FavoriteSeriesDao {
    @Insert
    fun insert(entity: FavoriteSeriesEntity)

    @Query("SELECT * from idb_favoriteSeries")
    fun retrieve(): List<FavoriteSeriesEntity>

    @Delete
    fun delete(entity: FavoriteSeriesEntity)

    @Query("SELECT * FROM idb_favoriteSeries WHERE id = :id")
    fun getFavoriteSeriesById(id: Long): FavoriteSeriesEntity?
}