package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.eamosse.idbdata.data.Movie

import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.databinding.FragmentHomeSecondBinding
import com.gmail.eamosse.imdb.ui.home.adapter.MovieAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.MovieHandler
import com.gmail.eamosse.imdb.ui.home.adapter.SerieAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.SerieHandler

class HomeSecondFragment : Fragment(), MovieHandler, SerieHandler {

    private val args: HomeSecondFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeSecondBinding
    private lateinit var name: String
    private var id: String = ""
    private var moviesForDetails: List<Movie> = emptyList()
    private var seriesForDetails: List<Movie> = emptyList()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.clearMovies()
        homeViewModel.clearSeries()
        moviesForDetails = emptyList()
        seriesForDetails = emptyList()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.adapter = MovieAdapter(emptyList(), this)

        id = args.myId
        name = args.GenreName
        with(homeViewModel){

            getMoviesByCategoryId(id.toInt())
            binding.serieList.isVisible = false
            getSeriesByCategoryId(id.toInt())



            homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.isVisible = isLoading
                binding.secondHomeTextViewNotFound.isVisible = !isLoading
            })

            movies.observe(viewLifecycleOwner, Observer { it ->
                    binding.secondHomeTextViewNotFound.isVisible = false
                    binding.movieList.adapter = MovieAdapter(it, this@HomeSecondFragment)
                    moviesForDetails = it
                    getSeriesByCategoryId(id.toInt())
            })

            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
                //Toast.makeText(context, "probléme de recuperation de la liste", Toast.LENGTH_SHORT).show()

            })




            homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.isVisible = isLoading
                binding.secondHomeTextViewNotFound.isVisible = !isLoading
            })

            series.observe(viewLifecycleOwner, Observer { it ->
                    binding.secondHomeTextViewNotFound.isVisible = false
                    binding.serieList.adapter = SerieAdapter(it, this@HomeSecondFragment)
            })

            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
                //Toast.makeText(context, "probléme de recuperation de la liste", Toast.LENGTH_SHORT).show()

            })

        }

        val selectedColor = this.activity?.let { ContextCompat.getColor(it, R.color.white) }
        val deselectedColor = this.activity?.let { ContextCompat.getColor(it, R.color.black) }

        if (selectedColor != null) {
            binding.moviesBtn.setBackgroundColor(selectedColor)
        } // Assuming Movies is selected by default
        if (deselectedColor != null) {
            binding.seriesBtn.setBackgroundColor(deselectedColor)
        }

        binding.toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if (checkedId == R.id.movies_btn ) {
                if (selectedColor != null && deselectedColor != null) {
                    binding.serieList.isVisible = false
                    binding.movieList.isVisible = true
                    binding.moviesBtn.setBackgroundColor(selectedColor)
                    binding.moviesBtn.setTextColor(deselectedColor)
                    binding.seriesBtn.setBackgroundColor(deselectedColor)
                    binding.seriesBtn.setTextColor(selectedColor)
                    getMovies()
                }
                // Handle Movies button logic here
            } else if (checkedId == R.id.series_btn ) {
                if (selectedColor != null && deselectedColor != null) {
                    binding.serieList.isVisible = true
                    binding.movieList.isVisible = false
                    binding.seriesBtn.setBackgroundColor(selectedColor)
                    binding.seriesBtn.setTextColor(deselectedColor)
                    binding.moviesBtn.setBackgroundColor(deselectedColor)
                    binding.moviesBtn.setTextColor(selectedColor)
                    getSeries()
                }
            }
            // Handle Series button logic here
        }


        }

    private fun getMovies(){

           if(moviesForDetails != null && moviesForDetails.isNotEmpty()){
               binding.secondHomeTextViewNotFound.isVisible = false
               binding.movieList.adapter = MovieAdapter(moviesForDetails, this@HomeSecondFragment)
           }
            else {
               binding.secondHomeTextViewNotFound.isVisible = true
               binding.secondHomeTextViewNotFound.text = "We can't find any $name films at the moment. But there are plenty of other genres to explore in the meantime!"
           }
    }

    private fun getSeries(){



            if(seriesForDetails.isNotEmpty()){
                binding.secondHomeTextViewNotFound.isVisible = false
                binding.movieList.adapter = MovieAdapter(seriesForDetails, this@HomeSecondFragment)
            }
            else {
                binding.secondHomeTextViewNotFound.isVisible = true
                binding.secondHomeTextViewNotFound.text = "We can't find any $name series at the moment. But there are plenty of other genres to explore in the meantime!"
            }


    }
    override fun onShowMovieDetails(id: Long, type: String) {
        val action = HomeSecondFragmentDirections
            .actionHomeSecondFragmentToMovieDetailsFragment(id.toString(), type)
        NavHostFragment.findNavController(this@HomeSecondFragment)
            .navigate(action)
    }

    override fun onShowEmptyListMsg() {
        binding.secondHomeTextViewNotFound.isVisible = true
        binding.secondHomeTextViewNotFound.text = "We can't find any $name films at the moment. But there are plenty of other genres to explore in the meantime!"
    }

    override fun removeEmptyListMsg() {
        binding.secondHomeTextViewNotFound.isVisible = false
    }

    override fun onShowSerieDetails(id: Long, type: String) {
        onShowMovieDetails(id, type)
    }


    override fun onShowEmptyListSerieMsg() {
        binding.secondHomeTextViewNotFound.text = "We can't find any $name series at the moment. But there are plenty of other genres to explore in the meantime!"
        binding.secondHomeTextViewNotFound.isVisible = true
    }

    override fun removeEmptyListSerieMsg() {
        binding.secondHomeTextViewNotFound.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        binding.toggleButtonGroup.check(R.id.movies_btn)
    }

    /*
    view.findViewById<Button>(R.id.button_home_second).setOnClickListener {
        findNavController().navigate(R.id.action_HomeSecondFragment_to_HomeFragment)
    }
     */
        /*
        view.findViewById<TextView>(R.id.textview_home_second).text =
                getString(R.string.hello_home_second, args.myArg)
         */

    }

