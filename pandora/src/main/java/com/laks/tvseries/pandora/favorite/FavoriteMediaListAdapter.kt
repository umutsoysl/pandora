package com.laks.tvseries.pandora.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.media.DBFavoriteListItemClickListener
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.databinding.LayoutFavoriteMediaItemBinding
import com.squareup.picasso.Picasso

class FavoriteMediaListAdapter(private val context: Context, private val clickListener: DBFavoriteListItemClickListener) : ListAdapter<DBMediaEntity, FavoriteMediaListAdapter.FavoriteMediaListViewHolder>(FavoriteMediaItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMediaListViewHolder {
        return FavoriteMediaListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteMediaListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class FavoriteMediaListViewHolder(private val binding: LayoutFavoriteMediaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: DBFavoriteListItemClickListener, itemData: DBMediaEntity) {
            binding.itemClickListener = clickListener
            binding.movie = itemData
            itemData.image.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.image}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteMediaListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutFavoriteMediaItemBinding.inflate(layoutInflater, parent, false)
                return FavoriteMediaListViewHolder(binding)
            }
        }
    }

    class FavoriteMediaItemModelDiffCallback : DiffUtil.ItemCallback<DBMediaEntity>() {
        override fun areItemsTheSame(oldItem: DBMediaEntity, newItem: DBMediaEntity) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DBMediaEntity, newItem: DBMediaEntity) = (oldItem == newItem)
    }
}