package com.gmail.eamosse.imdb.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Rating
import com.gmail.eamosse.idbdata.data.RatingBody
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
    private lateinit var _moviesForDetails: List<Movie>
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    /***Trailer****/
    private val _trailer: MutableLiveData<Trailer> = MutableLiveData()
    val trailer: LiveData<Trailer>
        get() = _trailer

    /***Rating****/
    private var _ratingResult: MutableLiveData<Boolean> = MutableLiveData()
    val ratingResult: LiveData<Boolean>
        get() = _ratingResult

    /***Favorite****/
    private var _favorite: MutableLiveData<Boolean> = MutableLiveData()
    val favorite: LiveData<Boolean>
        get() = _favorite


    private var _favoriteM: MutableLiveData<Movie?> = MutableLiveData()
    val favoriteM: LiveData<Movie?>
        get() = _favoriteM


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

    fun postAddRating(id: Int, rating: RatingBody) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.addRating(id, rating)) {
                is Result.Succes -> {
                    _isLoading.postValue(false)
                    _ratingResult.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }

                else -> {}
            }
        }
    }

    /**Probleme*/

    fun addToFavorites(id: Int){
        val currentMovies = _moviesForDetails ?: null // Return early if movies are null
        val movie = currentMovies?.find { it.id == id.toLong() }
        Log.i("favorite", "je suis dans viewModel")
        // getMovieById(id)?.let { repository.insertFavoriteMovie(it) }
        if (movie != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertFavoriteMovie(movie)
            }
        }
        else {
            Log.i("favorite", "je suis dans viewModel movie == null")
        }
    }

    private lateinit var _favoriteMovies : List<Movie>
    fun getFavoriteMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            /*
            _favoriteMovies.addSource(repository.getFavoriteMovies()){
                    entities -> _favoriteMovies.value = entities
            }

             */
            _favoriteMovies = repository.getFavoriteMovies()
        }
    }

    fun deleteFavoriteMovie(id: Int){
        val currentMovies = _moviesForDetails ?: null // Return early if movies are null
        val movie = currentMovies?.find { it.id == id.toLong() }
        Log.i("favorite", "je suis dans viewModel")
        // getMovieById(id)?.let { repository.insertFavoriteMovie(it) }
        if (movie != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteFavoriteMovie(movie)
            }
        }
        else {
            Log.i("favorite", "je suis dans viewModel movie == null")
        }
    }

    fun isFavorite(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getFavoriteMovieById(id) == null){
                _favoriteM.postValue(null)
            } else {
                _favoriteM.postValue(repository.getFavoriteMovieById(id))
            }

        }
    }



    fun clearMovies(){
        _moviesForDetails = _movies.value!!
        _movies.postValue(emptyList());
    }

    fun clearMovieDetails(){
        _trailer.postValue(Trailer(null, null, null, null, null, null, null, null, null, ""))
    }



}