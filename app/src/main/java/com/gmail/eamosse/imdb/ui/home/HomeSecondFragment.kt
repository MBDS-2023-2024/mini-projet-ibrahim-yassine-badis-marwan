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

class HomeSecondFragment : Fragment(), MovieHandler {

    private val args: HomeSecondFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeSecondBinding
    private lateinit var name: String
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.adapter = MovieAdapter(emptyList(), this)

        val id: String = args.myId
        name = args.GenreName
        with(homeViewModel){
            getMoviesByCategoryId(id.toInt())

            homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.isVisible = isLoading
                binding.secondHomeTextViewNotFound.isVisible = !isLoading
            })

            movies.observe(viewLifecycleOwner, Observer { it ->
                if (it.isNotEmpty()){
                    binding.secondHomeTextViewNotFound.isVisible = false
                    binding.secondHomeTextViewNotFound.isVisible = false
                    binding.movieList.adapter = MovieAdapter(it, this@HomeSecondFragment)
                }
                else {
                    binding.secondHomeTextViewNotFound.isVisible = true
                    binding.secondHomeTextViewNotFound.text = "We can't find any $name films at the moment. But there are plenty of other genres to explore in the meantime!"
                }

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
                    binding.moviesBtn.setBackgroundColor(selectedColor)
                    binding.moviesBtn.setTextColor(deselectedColor)
                    binding.seriesBtn.setBackgroundColor(deselectedColor)
                    binding.seriesBtn.setTextColor(selectedColor)
                    with(homeViewModel){

                    }
                }
                // Handle Movies button logic here
            } else if (checkedId == R.id.series_btn ) {
                if (selectedColor != null && deselectedColor != null) {
                    binding.seriesBtn.setBackgroundColor(selectedColor)
                    binding.seriesBtn.setTextColor(deselectedColor)
                    binding.moviesBtn.setBackgroundColor(deselectedColor)
                    binding.moviesBtn.setTextColor(selectedColor)
                }
            }
            // Handle Series button logic here
        }


        }

    override fun onShowMovieDetails(id: Long) {
        val action = HomeSecondFragmentDirections
            .actionHomeSecondFragmentToMovieDetailsFragment(id.toString())
        NavHostFragment.findNavController(this@HomeSecondFragment)
            .navigate(action)
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

