package com.laks.tvseries.featurecategory.detail.season

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.data.model.SeasonModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.databinding.LayoutSeasonItemBinding
import com.squareup.picasso.Picasso

class SeasonListAdapter(private val context: Context) : ListAdapter<SeasonModel, SeasonListAdapter.SeasonListViewHolder>(SeasonListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonListViewHolder {
        return SeasonListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SeasonListViewHolder, position: Int) {
        holder.bind(context, getItem(position))
    }

    class SeasonListViewHolder(private val binding: LayoutSeasonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, itemData: SeasonModel) {
            var episode = "0/${itemData.episodeCount} ${context.resources.getString(R.string.episodes)}"
            var overView = if(itemData.overview.length>70) {
                itemData.overview.substring(0, 71) + "..."
            } else {
                itemData.overview
            }
            binding.labelOverView.text = overView
            binding.labelEpisode.text = episode
            binding.season = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule) }

            binding.moreButton.setOnClickListener {
                binding.labelOverView.text = itemData.overview
                binding.moreButton.visibility = View.GONE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SeasonListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSeasonItemBinding.inflate(layoutInflater, parent, false)
                return SeasonListViewHolder(binding)
            }
        }
    }

    class SeasonListItemModelDiffCallback : DiffUtil.ItemCallback<SeasonModel>() {
        override fun areItemsTheSame(oldItem: SeasonModel, newItem: SeasonModel) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: SeasonModel, newItem: SeasonModel) = (oldItem == newItem)
    }
}