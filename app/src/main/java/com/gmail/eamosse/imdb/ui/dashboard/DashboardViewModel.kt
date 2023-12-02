package com.gmail.eamosse.imdb.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
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

    /***Error****/
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    /***Token****/
    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token
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
                    Log.d("1111111111111", "Number of Popular Movies: ${result}")

                    _popularMovies.postValue(result.data)
                }

                is Result.Error -> {
                    Log.d("1111111111111", "Number of Popular Movies: ${result}")

                    _error.postValue(result.message)
                }

                else -> {}
            }
        }

    }




}