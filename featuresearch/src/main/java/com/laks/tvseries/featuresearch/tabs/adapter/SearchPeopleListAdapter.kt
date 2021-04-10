package com.laks.tvseries.featuresearch.tabs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresearch.databinding.LayoutSearchPeopleItemBinding
import com.squareup.picasso.Picasso

class SearchPeopleListAdapter(private val context: Context, private val clickListener: PeopleItemClickListener) : ListAdapter<PersonInfo, SearchPeopleListAdapter.AllPeopleListViewHolder>(AllPeopleListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPeopleListViewHolder {
        return AllPeopleListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AllPeopleListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class AllPeopleListViewHolder(private val binding: LayoutSearchPeopleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: PeopleItemClickListener, itemData: PersonInfo) {
            binding.itemClickListener = clickListener
            binding.people = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AllPeopleListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSearchPeopleItemBinding.inflate(layoutInflater, parent, false)
                return AllPeopleListViewHolder(binding)
            }
        }
    }

    class AllPeopleListItemModelDiffCallback : DiffUtil.ItemCallback<PersonInfo>() {
        override fun areItemsTheSame(oldItem: PersonInfo, newItem: PersonInfo) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PersonInfo, newItem: PersonInfo) = (oldItem == newItem)
    }
}