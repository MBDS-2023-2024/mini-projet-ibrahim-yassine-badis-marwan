package com.gmail.eamosse.imdb.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.eamosse.idbdata.api.response.WatchProvidersResponse
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.MovieProvider.CountryResult
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.data.Trailer
import com.gmail.eamosse.idbdata.repository.MovieRepository
import com.gmail.eamosse.idbdata.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    /***Category****/
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>>
        get() = _categories

    /***Token****/
    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    /***Error****/
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    /***Loading****/
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    /***Movies****/
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    /***Trailer****/
    private val _trailer: MutableLiveData<Trailer> = MutableLiveData()
    val trailer: LiveData<Trailer>
        get() = _trailer

    /***Provider****/
    private val _provider: MutableLiveData<WatchProvidersResponse.CountryResult> = MutableLiveData()
    val provider: MutableLiveData<WatchProvidersResponse.CountryResult>
        get() = _provider

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.getToken()) {
                is Result.Succes -> {
                    _token.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }

                else -> {}
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCategories()) {
                is Result.Succes -> {
                    _categories.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }

                else -> {}
            }
        }
    }

    fun getMoviesByCategoryId(id: Int) {
        _isLoading.postValue(true) // Début du chargement
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getMoviesByCategoryId(id)) {
                is Result.Succes -> {
                    _isLoading.postValue(false)
                    _movies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }

                else -> {}
            }
        }
    }

    fun getMovieById(id: Int): Movie? {
        val currentMovies = _movies.value ?: return null // Return early if movies are null

        val movie = currentMovies.find { it.id == id } // Find the movie with the given ID
        if (movie != null) {
            return movie
        } else {
            return null
            _error.postValue("Movie not found") // Handle the case where the movie is not in the list
        }

    }

    fun getTrailerByMovieId(id: Int) {
        _isLoading.postValue(true) // Début du chargement
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getTrailerByMovieId(id)) {
                is Result.Succes -> {
                    _isLoading.postValue(false)
                    _trailer.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }

                else -> {}
            }
        }
    }

    fun getProvidersByMovieId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getProvidersByMovieId(id)) {
                is Result.Succes -> {
                    Log.d("repo DDDDDD", "Displaying movie title: ${result.data}")
                    _provider.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
                else -> {}
            }
        }
    }

   fun clearMovies(){
       _movies.postValue(emptyList());
   }

   fun clearMovieDetails(){
       _trailer.postValue(Trailer(null, null, null, null, null, null, null, null, null, ""))
   }



}