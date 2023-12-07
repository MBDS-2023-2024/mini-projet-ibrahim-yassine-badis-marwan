package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Rating
import com.gmail.eamosse.idbdata.data.RatingBody
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeMovieDetailsBinding
import com.gmail.eamosse.imdb.ui.home.adapter.PopularPeopleAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.PopularPeopleHandler


class HomeMovieDetailsFragment : Fragment(), PopularPeopleHandler {

    private val args: HomeMovieDetailsFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeMovieDetailsBinding
    private var movie: Movie? = null
    private var serie: Serie? = null
    private var isFavorite = false
    private lateinit var id: String
    private lateinit var type: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = args.id
        type = args.type

        with(homeViewModel){

            /***PopularPerson***/
            getAllPopularPersons()

           popularPersons.observe(viewLifecycleOwner, Observer{
               val recyclerView = binding.recyclerPopularPeople
               val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
               recyclerView.layoutManager = layoutManager
               recyclerView.adapter = PopularPeopleAdapter(it, this@HomeMovieDetailsFragment)
           })




            /**Favorite**/
            if (type == "movie"){
                isFavorite(id.toLong())
                //getFavoriteMovies()

                favoriteM.observe(viewLifecycleOwner) {
                    if (it != null){
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                        isFavorite = true
                    }
                    else {
                        binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        isFavorite = false
                    }
                };

            }
            else {
                isFavoriteSeries(id.toLong())
                //getFavoriteMovies()

                favoriteS.observe(viewLifecycleOwner) {
                    if (it != null){
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                        isFavorite = true
                    }
                    else {
                        binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        isFavorite = false
                    }
                };

            }

            if (type == "movie"){
                movie = getMovieById(id.toInt())
                getTrailerByMovieId(id.toInt())
            }
            else {
                serie = getSerieById(id.toInt())
                getTrailerBySeriesId(id.toInt())
            }



            if (type == "movie" && movie != null){
                displayMovieInfo()
            }
            else if (type == "serie" && serie != null){
                displaySerieInfo()
            }
            else {
                // displayErrorMessage()
            }

            homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.isVisible = isLoading
            })

            trailer.observe(viewLifecycleOwner, Observer {
                displayVideo(it.key)
            })

            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
                //Toast.makeText(context, "problÃ©me de recuperation de trailer", Toast.LENGTH_SHORT).show()
                //displayVideo(it.key)
                binding.progressBar.isVisible = false
            })
        }

        binding.movieImage.setOnClickListener {
            makeItINFavorite()
        }

        binding.btnFavorite.setOnClickListener {
            makeItINFavorite()
        }

        binding.editTextNote.setOnEditorActionListener {  v, actionId, event->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                with(homeViewModel){
                    postAddRating(id.toInt(), RatingBody(binding.editTextNote.text.toString().toDouble()))
                    homeViewModel.ratingResult.observe(viewLifecycleOwner) {
                        if (it == true) {
                            Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(context, "probleme", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }else {
                false
            }
        }

    }

    private fun makeItINFavorite() {
        isFavorite = !isFavorite

        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
            with(homeViewModel){
                if (type == "movie"){
                    addToFavorites(id.toInt());
                }
                else {
                    addToFavoriteSeries(id.toInt());
                }

            }
        } else {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            with(homeViewModel){
                if (type == "movie"){
                    deleteFavoriteMovie(id.toInt());
                }
                else {
                    deleteFavoriteSeries(id.toInt())
                }

            }
        }
    }



    private fun displayMovieInfo() {
        displayImage(movie?.posterPath)
        binding.movieRating.text = getString(R.string.rating) + ": " + movie?.voteAverage + " ( "+ movie?.voteCount + " " + getString(R.string.votes) + ")"
        binding.movieTitle.text = movie?.title
        binding.movieDescription.text = movie?.overview
        binding.movieReleaseDate.text = getString(R.string.releaseDate)+ ": " + movie?.releaseDate
    }

    private fun displaySerieInfo() {
        displayImage(serie?.posterPath)
        binding.movieRating.text = getString(R.string.rating) + ": " + serie?.voteAverage + " ( "+ serie?.voteCount + " " + getString(R.string.votes) + ")"
        binding.movieTitle.text = serie?.originalName
        binding.movieDescription.text = serie?.overview
        binding.movieReleaseDate.text = getString(R.string.releaseDate)+ ": " + serie?.firstAirDate
    }

    private fun displayImage(posterPath: String?) {
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseUrl + posterPath

        Glide.with(binding.root.context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.movie_placeholder)
            .error(R.drawable.movie_placeholder)
            .into(binding.movieImage)

    }

    private fun displayVideo(key: String?) {
        if (key != null){
            binding.videoNotFound.isVisible = false
            binding.webviewTrailer.isVisible = true
            val webView: WebView = binding.webviewTrailer
            webView.settings.javaScriptEnabled = true
            val youtubeVideoHtml = """
        <html>
        <body style="background-color: black;">
        <iframe  width="100%" height="100%" src="https://www.youtube.com/embed/$key" frameborder="0" allowfullscreen></iframe>
        </body>
        </html>
        """
            webView.loadData(youtubeVideoHtml, "text/html", "utf-8")
        }
        else {
            binding.videoNotFound.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.clearMovieDetails()

    }

    override fun onShowPeopleDetails(id: Int) {

    }

    override fun onShowEmptyListPeopleMsg() {

    }

    override fun removeEmptyListPeopleMsg() {

    }


}