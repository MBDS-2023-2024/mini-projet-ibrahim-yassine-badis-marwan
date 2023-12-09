package com.gmail.eamosse.imdb.ui.home.adapter

interface MovieHandler {
    fun onShowMovieDetails(id: Long, type: String)
    fun onShowEmptyListMsg()

    fun removeEmptyListMsg()
}