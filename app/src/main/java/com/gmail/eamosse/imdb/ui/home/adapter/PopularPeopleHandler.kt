package com.gmail.eamosse.imdb.ui.home.adapter

interface PopularPeopleHandler {
    fun onShowPeopleDetails(id: Int)
    fun onShowEmptyListPeopleMsg()
    fun removeEmptyListPeopleMsg()
}