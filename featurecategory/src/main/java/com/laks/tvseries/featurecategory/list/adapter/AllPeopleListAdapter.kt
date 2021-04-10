package com.laks.tvseries.featurecategory.list.adapter

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
import com.laks.tvseries.featurecategory.databinding.LayoutAllPeopleItemBinding
import com.squareup.picasso.Picasso

class AllPeopleListAdapter(private val context: Context, private val clickListener: PeopleItemClickListener) : ListAdapter<PersonInfo, AllPeopleListAdapter.AllPeopleListViewHolder>(AllPeopleListItemModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPeopleListViewHolder {
        return AllPeopleListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AllPeopleListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class AllPeopleListViewHolder(private val binding: LayoutAllPeopleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: PeopleItemClickListener, itemData: PersonInfo) {
            binding.itemClickListener = clickListener
            binding.people = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imageSchedule)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AllPeopleListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutAllPeopleItemBinding.inflate(layoutInflater, parent, false)
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