package com.gmail.eamosse.imdb.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.data.Trailer
import com.gmail.eamosse.idbdata.repository.MovieRepository
import com.gmail.eamosse.idbdata.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()

    val popularMovies: LiveData<List<Movie>> get() = _popularMovies
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> get() = _upcomingMovies

    private var _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>> get() = _favoriteMovies

    /***Error****/
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    /***Token****/
    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    /***Movies****/
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    /***Loading****/
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    /***Trailer****/
    private val _trailer: MutableLiveData<Trailer> = MutableLiveData()
    val trailer: LiveData<Trailer>
        get() = _trailer
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
    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getPopularMovies()) {
                is Result.Succes ->{
                    _popularMovies.postValue(result.data)
                }

                is Result.Error -> {

                    _error.postValue(result.message)
                }

                else -> {}
            }
        }

    }

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getTopRatedMovies()) {
                is Result.Succes ->{
                    _topRatedMovies.postValue(result.data)
                }

                is Result.Error -> {

                    _error.postValue(result.message)
                }

                else -> {}
            }
        }

    }
    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getUpcomingMovies()) {
                is Result.Succes ->{
                    _upcomingMovies.postValue(result.data)
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

        val movie = currentMovies.find { it.id == id.toLong() } // Find the movie with the given ID
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
    fun clearMovies(){
        _movies.postValue(emptyList());
    }

    fun clearMovieDetails(){
        _trailer.postValue(Trailer(null, null, null, null, null, null, null, null, null, ""))
    }
    fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favoriteMovies = repository.getFavoriteMovies()
                _favoriteMovies.postValue(favoriteMovies)
            } catch (e: Exception) {
                // Handle the exception or log an error message
                _error.postValue("Error fetching favorite movies: ${e.message}")
            }
        }
    }

}