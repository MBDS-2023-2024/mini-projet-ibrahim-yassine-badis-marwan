package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.CategoryListItemBinding


class CategoryAdapter(private val items: List<Category>, categoryAdapterHandler: CategoryAdapterHandler) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val mCategoryAdapterHandler = categoryAdapterHandler

    inner class ViewHolder(private val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.item = item

            when (item.name) {
                "Action" -> binding.categoryImg.setImageResource(R.mipmap.action)
                "Adventure" -> binding.categoryImg.setImageResource(R.mipmap.aventure)
                "Animation" -> binding.categoryImg.setImageResource(R.mipmap.animation)
                "Comedy" -> binding.categoryImg.setImageResource(R.mipmap.comedy)
                "Crime" -> binding.categoryImg.setImageResource(R.mipmap.crime)
                "Documentary" -> binding.categoryImg.setImageResource(R.mipmap.documentary)
                "Drama" -> binding.categoryImg.setImageResource(R.mipmap.drama)
                "Family" -> binding.categoryImg.setImageResource(R.mipmap.family)
                "Fantasy" -> binding.categoryImg.setImageResource(R.mipmap.fantasy)
                "History" -> binding.categoryImg.setImageResource(R.mipmap.history)
                "Horror" -> binding.categoryImg.setImageResource(R.mipmap.horror)
                "Music" -> binding.categoryImg.setImageResource(R.mipmap.music)
                "Mystery" -> binding.categoryImg.setImageResource(R.mipmap.mystery)
                "Romance" -> binding.categoryImg.setImageResource(R.mipmap.romance)
                "Science Fiction" -> binding.categoryImg.setImageResource(R.mipmap.sciencefiction)
                "TV Movie" -> binding.categoryImg.setImageResource(R.mipmap.tvmovie)
                "Thriller" -> binding.categoryImg.setImageResource(R.mipmap.thriller)
                "War" -> binding.categoryImg.setImageResource(R.mipmap.war)
                "Western" -> binding.categoryImg.setImageResource(R.mipmap.wastern)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CategoryListItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        val category = items[position]
        holder.itemView.setOnClickListener{
            mCategoryAdapterHandler.onShowMoviesByCategory(category.id, category.name)
        }
    }
}