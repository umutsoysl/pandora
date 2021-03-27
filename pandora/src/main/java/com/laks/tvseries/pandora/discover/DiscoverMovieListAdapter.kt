package com.laks.tvseries.pandora.discover

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.databinding.MovieListItemBinding
import com.squareup.picasso.Picasso

class DiscoverMovieListAdapter(private val context: Context, private val clickListener: DiscoverMovieListItemOnClickListener) : ListAdapter<MovieModel, DiscoverMovieListAdapter.HomeMovieListViewHolder>(HomeMovieListItemModelDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieListViewHolder {
        return HomeMovieListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMovieListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class HomeMovieListViewHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: DiscoverMovieListItemOnClickListener, itemData: MovieModel) {
            binding.itemClickListener = clickListener
            binding.schedule = itemData
            itemData.posterPath.let {Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HomeMovieListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(layoutInflater, parent, false)
                return HomeMovieListViewHolder(binding)
            }
        }
    }

    class HomeMovieListItemModelDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) = (oldItem == newItem)
    }
}