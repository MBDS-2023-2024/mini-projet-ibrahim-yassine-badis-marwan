package com.gmail.eamosse.imdb.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentDashboardBinding
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.ui.home.HomeSecondFragmentArgs
import com.gmail.eamosse.imdb.ui.home.HomeSecondFragmentDirections
import com.gmail.eamosse.imdb.ui.home.HomeViewModel
import com.gmail.eamosse.imdb.ui.home.adapter.CategoryAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.MovieAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.MovieHandler
import com.gmail.eamosse.imdb.ui.home.adapter.PopularPeopleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(), MovieHandler {
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var topRatedMoviesRecyclerView: RecyclerView
    private lateinit var upcomingMoviesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularMoviesRecyclerView = binding.recyclerPopularMovies
        setupRecyclerView(popularMoviesRecyclerView)

        topRatedMoviesRecyclerView = binding.recyclerTopRatedMovies
        setupRecyclerView(topRatedMoviesRecyclerView)

        upcomingMoviesRecyclerView = binding.recyclerUpcomingMovies
        setupRecyclerView(upcomingMoviesRecyclerView)

        dashboardViewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
            (popularMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(popularMovies)
        })

        dashboardViewModel.topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
            (topRatedMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(topRatedMovies)
        })

        dashboardViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->
            (upcomingMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(upcomingMovies)
        })



        /*

        with(dashboardViewModel){
            topRatedMovies.observe(viewLifecycleOwner, Observer {
                val recyclerView = binding.recyclerTopRatedMovies
                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = MovieAdapter(it, this@DashboardFragment)
            })
        }

         */




        dashboardViewModel.getPopularMovies()
        dashboardViewModel.getTopRatedMovies()
        dashboardViewModel.getUpcomingMovies()

    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        // You need to create a MovieAdapter and set it to the recyclerView adapter
        val movieAdapter = MovieAdapter(emptyList(), object : MovieHandler {
            override fun onShowMovieDetails(id: Long, type: String) {
                Toast.makeText(context, "hrllo", Toast.LENGTH_SHORT).show()
                val action = DashboardFragmentDirections
                    .actionDashboardFragmentToMovieDetailsFragment(id.toString(), "movie")
                NavHostFragment.findNavController(this@DashboardFragment)
                    .navigate(action)
            }

            override fun onShowEmptyListMsg() {

            }

            override fun removeEmptyListMsg() {

            }
        })
        recyclerView.adapter = movieAdapter
    }

    override fun onShowMovieDetails(id: Long, type: String) {

    }

    override fun onShowEmptyListMsg() {

    }

    override fun removeEmptyListMsg() {

    }


}
