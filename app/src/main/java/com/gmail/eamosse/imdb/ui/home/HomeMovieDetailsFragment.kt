package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeMovieDetailsBinding
import com.gmail.eamosse.imdb.databinding.FragmentHomeSecondBinding
import com.gmail.eamosse.imdb.ui.home.adapter.CategoryAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.MovieAdapter


class HomeMovieDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val args: HomeMovieDetailsFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeMovieDetailsBinding
    private var movie: Movie? = null
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val id: String = args.id

        with(homeViewModel){
            movie = getMovieById(id.toInt())
            getTrailerByMovieId(id.toInt())
            getProvidersByMovieId(id.toInt())

            if (movie != null){
                displayMovieInfo()
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

            /*
            provider.observe(viewLifecycleOwner, Observer {
                displayVideo(it.key)
            })
            */


            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
                Toast.makeText(context, "probléme de recuperation de trailer", Toast.LENGTH_SHORT).show()
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

    }

    private fun makeItINFavorite() {
        isFavorite = !isFavorite

        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun displayMovieInfo() {
        displayImage(movie?.posterPath)
        binding.movieRating.text = getString(R.string.rating) + ": " + movie?.voteAverage + " ( "+ movie?.voteCount + " " + getString(R.string.votes) + ")"
        binding.movieTitle.text = movie?.title
        binding.movieDescription.text = movie?.overview
        binding.movieReleaseDate.text = getString(R.string.releaseDate)+ ": " + movie?.releaseDate
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


}