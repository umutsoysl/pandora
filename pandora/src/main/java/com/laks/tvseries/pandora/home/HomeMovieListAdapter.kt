package com.laks.tvseries.pandora.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.data.model.TVShowModel
import com.laks.tvseries.pandora.databinding.MovieListItemBinding
import com.squareup.picasso.Picasso

class HomeMovieListAdapter(private val context: Context, private val clickListener: HomeMovieListItemOnClickListener) : ListAdapter<TVShowModel, HomeMovieListAdapter.HomeMovieListViewHolder>(HomeMovieListItemModelDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieListViewHolder {
        return HomeMovieListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMovieListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class HomeMovieListViewHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: HomeMovieListItemOnClickListener, itemData: TVShowModel) {
            binding.itemClickListener = clickListener
            binding.schedule = itemData
            itemData.image.let {Picasso.with(context).load(itemData.image?.medium).fit().into(binding.imageSchedule)}
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

    class HomeMovieListItemModelDiffCallback : DiffUtil.ItemCallback<TVShowModel>() {
        override fun areItemsTheSame(oldItem: TVShowModel, newItem: TVShowModel) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: TVShowModel, newItem: TVShowModel) = (oldItem == newItem)
    }
}