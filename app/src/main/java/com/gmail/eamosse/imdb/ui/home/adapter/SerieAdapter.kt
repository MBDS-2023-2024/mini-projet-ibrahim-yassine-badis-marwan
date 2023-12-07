package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.MovieListItemBinding
import com.gmail.eamosse.imdb.databinding.SerieListItemBinding

class SerieAdapter(private val items: List<Serie>, serieHandler: SerieHandler) :
    RecyclerView.Adapter<SerieAdapter.ViewHolder>(){

    private val mSerieHandler = serieHandler

    inner class ViewHolder(private val binding: SerieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Serie) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SerieListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SerieAdapter.ViewHolder, position: Int) {
        if (items.isEmpty()){
            this.mSerieHandler.onShowEmptyListSerieMsg()
        }
        else {
            this.mSerieHandler.removeEmptyListSerieMsg()
        }
        holder.bind(items[position])
        val serie = items[position]

        holder.itemView.setOnClickListener{
            this.mSerieHandler.onShowSerieDetails(serie.id.toLong(), "serie")
        }

    }

    override fun getItemCount(): Int = items.size

}