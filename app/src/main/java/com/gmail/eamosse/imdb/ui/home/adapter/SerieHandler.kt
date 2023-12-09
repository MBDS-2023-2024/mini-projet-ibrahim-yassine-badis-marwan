package com.gmail.eamosse.imdb.ui.home.adapter

interface SerieHandler {

    fun onShowSerieDetails(id: Long, type: String)
    fun onShowEmptyListSerieMsg()

    fun removeEmptyListSerieMsg()
}