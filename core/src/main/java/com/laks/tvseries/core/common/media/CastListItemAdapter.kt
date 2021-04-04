package com.laks.tvseries.core.common.media

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.databinding.LayoutCastListItemBinding
import com.laks.tvseries.core.global.GlobalConstants
import com.squareup.picasso.Picasso

class CastListItemAdapter(private val context: Context, private val clickListener: CastListItemOnClickListener) : ListAdapter<CastObject, CastListItemAdapter.CastMovieListViewHolder>(
   CastMovieListItemModelDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMovieListViewHolder {
        return CastMovieListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CastMovieListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class CastMovieListViewHolder(private val binding: LayoutCastListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: CastListItemOnClickListener, itemData: CastObject) {
            binding.itemClickListener = clickListener
            binding.cast = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CastMovieListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutCastListItemBinding.inflate(layoutInflater, parent, false)
                return CastMovieListViewHolder(binding)
            }
        }
    }

    class CastMovieListItemModelDiffCallback : DiffUtil.ItemCallback<CastObject>() {
        override fun areItemsTheSame(oldItem: CastObject, newItem: CastObject) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CastObject, newItem: CastObject) = (oldItem == newItem)
    }
}