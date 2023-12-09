package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.CategoryListItemBinding
import com.gmail.eamosse.imdb.databinding.MovieListItemBinding


class MovieAdapter(private var items: List<Movie>, movieHandler: MovieHandler) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    private val mMovieHandler = movieHandler

    inner class ViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {

            binding.item = item

            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + item.posterPath

            Glide.with(binding.root.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.baseline_video_label_24)
                .error(R.drawable.baseline_video_label_24)
                .into(binding.movieImg)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(MovieListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        if (items.isEmpty()){
            this.mMovieHandler.onShowEmptyListMsg()
        }
        else {
            this.mMovieHandler.removeEmptyListMsg()
        }
        holder.bind(items[position])
        val movie = items[position]

        holder.itemView.setOnClickListener{
            this.mMovieHandler.onShowMovieDetails(movie.id, "movie")
        }

    }
    fun setItems(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = items.size

}