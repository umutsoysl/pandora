package com.laks.tvseries.featurecategory.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.media.GenreListItemOnClickListener
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.featurecategory.databinding.LayoutGenreItemBinding

class GenreListItemAdapter(private val context: Context, private val clickListener: GenreListItemOnClickListener) : ListAdapter<Genre, GenreListItemAdapter.GenreListViewHolder>(GenreListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        return GenreListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(context, clickListener , getItem(position))
    }

    class GenreListViewHolder(private val binding: LayoutGenreItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: GenreListItemOnClickListener,  itemData: Genre) {
            binding.itemClickListener = clickListener
            binding.genre = itemData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GenreListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutGenreItemBinding.inflate(layoutInflater, parent, false)
                return GenreListViewHolder(binding)
            }
        }
    }

    class GenreListItemModelDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = (oldItem.id == newItem.id)
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = (oldItem == newItem)
    }
}