package com.gmail.eamosse.imdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Review
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.ReviewListItemBinding

class ReviewAdapter(private val items: List<Review>, reviewHandler: ReviewHandler) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    private val mReviewHandler = reviewHandler

    inner class ViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {

            binding.item = item

            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + item.authorDetails.avatarPath

            Glide.with(binding.root.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.userImage)

            binding.userName.text = item.authorDetails.name
            if (item.authorDetails.rating != null){
                binding.userRating.isVisible = true
                binding.userRating.text = item.authorDetails.rating.toString()
            }
            else {
                binding.userRating.isVisible = false
            }


            binding.commentText.text = item.content

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ReviewListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
        if (items.isEmpty()){
            this.mReviewHandler.onShowEmptyListReviews()
        }
        else {
            this.mReviewHandler.removeEmptyListReviews()
        }
    }


    override fun getItemCount(): Int = items.size

    /*
       private val mReviewHandler = reviewHandler

    inner class ViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {

            binding.item = item

            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val imageUrl = baseUrl + item.authorDetails.avatarPath

            Glide.with(binding.root.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(binding.userImage)

            binding.userName.text = item.authorDetails.name
            binding.userRating.text = item.authorDetails.rating.toString()
            binding.commentText.text = item.content

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ReviewListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
        if (items.isEmpty()){
            this.mReviewHandler.onShowEmptyListReviews()
        }
        else {
            this.mReviewHandler.removeEmptyListReviews()
        }
    }
     */

}