package com.laks.tvseries.featureactor.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.media.CastListItemOnClickListener
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featureactor.databinding.ActorMediaItemBinding
import com.squareup.picasso.Picasso

class ActorMediaListAdapter(private val context: Context, private val clickListener: CastListItemOnClickListener) : ListAdapter<CastObject, ActorMediaListAdapter.ActorMovieListViewHolder>(ActorMovieListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieListViewHolder {
        return ActorMovieListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActorMovieListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class ActorMovieListViewHolder(private val binding: ActorMediaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: CastListItemOnClickListener, itemData: CastObject) {
            binding.itemClickListener = clickListener
            binding.schedule = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ActorMovieListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActorMediaItemBinding.inflate(layoutInflater, parent, false)
                return ActorMovieListViewHolder(binding)
            }
        }
    }

    class ActorMovieListItemModelDiffCallback : DiffUtil.ItemCallback<CastObject>() {
        override fun areItemsTheSame(oldItem: CastObject, newItem: CastObject) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CastObject, newItem: CastObject) = (oldItem == newItem)
    }
}