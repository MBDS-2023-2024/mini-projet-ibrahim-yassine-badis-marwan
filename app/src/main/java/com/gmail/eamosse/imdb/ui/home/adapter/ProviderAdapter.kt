package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.eamosse.idbdata.api.response.WatchProvidersResponse
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeMovieDetailsBinding


class ProviderAdapter(private val items: List<WatchProvidersResponse.MovieProvider>) :
    RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentHomeMovieDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WatchProvidersResponse.MovieProvider) {
            binding.itemProvider = item

            // Charger l'image du logo du fournisseur
            val baseUrl = "base_url_here"
            val imageUrl = baseUrl + item.logoPath

            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_baseline_error_24) // Remplacez par votre image de placeholder
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.movieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(FragmentHomeMovieDetailsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProviderAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}