package com.laks.tvseries.pandora.discover.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.media.GenreListItemOnClickListener
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.pandora.databinding.LayoutGenreChecklistItemBinding


class GenreCheckListAdapter(private val context: Context, private val clickListener: GenreListItemOnClickListener, private val isMovie: Boolean = true) : ListAdapter<Genre, GenreCheckListAdapter.GenreListViewHolder>(GenreListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        return GenreListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position), isMovie)
    }

    class GenreListViewHolder(private val binding: LayoutGenreChecklistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: GenreListItemOnClickListener, itemData: Genre, isMovie: Boolean) {
            itemData.isMovie = isMovie
            binding.genre = itemData

            binding.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
                itemData.isSelect = isChecked
                clickListener.genreListItemOnClickListener(itemData)
            })

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GenreListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutGenreChecklistItemBinding.inflate(layoutInflater, parent, false)
                return GenreListViewHolder(binding)
            }
        }
    }

    class GenreListItemModelDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = (oldItem.id == newItem.id)
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = (oldItem == newItem)
    }
}