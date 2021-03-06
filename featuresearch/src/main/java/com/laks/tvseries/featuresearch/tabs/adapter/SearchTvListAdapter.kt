package com.laks.tvseries.featuresearch.tabs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresearch.databinding.LayoutSearchMovieItemBinding
import com.squareup.picasso.Picasso

class SearchTvListAdapter(private val context: Context, private val clickListener: MediaListItemOnClickListener) : ListAdapter<MovieModel, SearchTvListAdapter.SearchTvListViewHolder>(SearchTvListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTvListViewHolder {
        return SearchTvListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchTvListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class SearchTvListViewHolder(private val binding: LayoutSearchMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: MediaListItemOnClickListener, itemData: MovieModel) {
            binding.itemClickListener = clickListener
            binding.movie = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchTvListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSearchMovieItemBinding.inflate(layoutInflater, parent, false)
                return SearchTvListViewHolder(binding)
            }
        }
    }

    class SearchTvListItemModelDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) = (oldItem == newItem)
    }
}