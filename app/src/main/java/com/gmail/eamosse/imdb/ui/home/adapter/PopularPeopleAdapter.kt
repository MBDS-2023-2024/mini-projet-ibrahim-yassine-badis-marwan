package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmail.eamosse.idbdata.data.PopularPerson
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.PopularpeopleListItemBinding
import com.gmail.eamosse.imdb.databinding.SerieListItemBinding

class PopularPeopleAdapter(private val items: List<PopularPerson>, popularPeopleHandler: PopularPeopleHandler):
    RecyclerView.Adapter<PopularPeopleAdapter.ViewHolder>(){

    private val mPopularPeopleHandler= popularPeopleHandler

    inner class ViewHolder(private val binding: PopularpeopleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularPerson) {

            binding.item = item

            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + item.profilePath

            Glide.with(binding.root.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.peopleImg)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(PopularpeopleListItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (items.isEmpty()){
            this.mPopularPeopleHandler.onShowEmptyListPeopleMsg()
        }
        else {
            this.mPopularPeopleHandler.removeEmptyListPeopleMsg()
        }

        holder.bind(items[position])
        val popularPeople = items[position]

        holder.itemView.setOnClickListener{
            // this.mSerieHandler.onShowSerieDetails(serie.id.toLong(), "serie")
        }
    }


}