package com.gmail.eamosse.imdb.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.imdb.databinding.FragmentDashboardBinding
import com.gmail.eamosse.imdb.ui.home.adapter.MovieAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.MovieHandler
import com.gmail.eamosse.imdb.ui.home.adapter.SerieAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.SerieHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(), MovieHandler {
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var topRatedMoviesRecyclerView: RecyclerView
    private lateinit var upcomingMoviesRecyclerView: RecyclerView
    private  lateinit var favoriteMoviesRecyclerView: RecyclerView
   private  lateinit var favoriteSeriesRecyclerView: RecyclerView

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
        setupRecyclerView(popularMoviesRecyclerView, AdapterType.MOVIE)

        topRatedMoviesRecyclerView = binding.recyclerTopRatedMovies
        setupRecyclerView(topRatedMoviesRecyclerView, AdapterType.MOVIE)

        upcomingMoviesRecyclerView = binding.recyclerUpcomingMovies
        setupRecyclerView(upcomingMoviesRecyclerView, AdapterType.MOVIE)

        favoriteMoviesRecyclerView = binding.recyclerFavoriteMovies
        setupRecyclerView(favoriteMoviesRecyclerView, AdapterType.MOVIE)

        favoriteSeriesRecyclerView = binding.recyclerFavoriteSeries
        setupRecyclerView(favoriteSeriesRecyclerView, AdapterType.SERIE)

        dashboardViewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
            (popularMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(popularMovies)
        })

        dashboardViewModel.topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
            (topRatedMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(topRatedMovies)
        })

        dashboardViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->
            (upcomingMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(upcomingMovies)
        })

        dashboardViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer { favoriteMovies ->
            (favoriteMoviesRecyclerView.adapter as? MovieAdapter)?.setItems(favoriteMovies)
        })

        dashboardViewModel.favoriteSeries.observe(viewLifecycleOwner, Observer { favoriteSeries ->
            (favoriteSeriesRecyclerView.adapter as? SerieAdapter)?.setItems(favoriteSeries)
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
        dashboardViewModel.getFavoriteMovies()
        dashboardViewModel.getFavoriteSeries()


    }


    private fun setupRecyclerView(recyclerView: RecyclerView, adapterType: AdapterType) {
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        when (adapterType) {
            AdapterType.MOVIE -> {
                val movieAdapter = MovieAdapter(emptyList(), object : MovieHandler {
                    override fun onShowMovieDetails(id: Long, type: String) {
                        val action = DashboardFragmentDirections
                            .actionDashboardFragmentToMovieDetailsFragment(id.toString(), type)
                        NavHostFragment.findNavController(this@DashboardFragment)
                            .navigate(action)
                    }

                    override fun onShowEmptyListMsg() {
                        // Handle empty list message for movies if needed
                    }

                    override fun removeEmptyListMsg() {
                        // Remove empty list message for movies if needed
                    }
                })
                recyclerView.adapter = movieAdapter
            }
            AdapterType.SERIE -> {
                val serieAdapter = SerieAdapter(emptyList(), object : SerieHandler {
                    override fun onShowSerieDetails(id: Long, type: String) {
                        val action = DashboardFragmentDirections
                            .actionDashboardFragmentToMovieDetailsFragment(id.toString(), type)
                        NavHostFragment.findNavController(this@DashboardFragment)
                            .navigate(action)
                    }

                    override fun onShowEmptyListSerieMsg() {
                        // Handle empty list message for series if needed
                    }

                    override fun removeEmptyListSerieMsg() {
                        // Remove empty list message for series if needed
                    }
                })
                recyclerView.adapter = serieAdapter
            }
        }
    }
    enum class AdapterType {
        MOVIE,
        SERIE
    }


    override fun onShowMovieDetails(id: Long, type: String) {

    }

    override fun onShowEmptyListMsg() {

    }

    override fun removeEmptyListMsg() {

    }


}
