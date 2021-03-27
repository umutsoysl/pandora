package com.laks.tvseries.core.common.people

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.databinding.LayoutPersonItemBinding
import com.laks.tvseries.core.global.GlobalConstants
import com.squareup.picasso.Picasso

class PeopleListItemAdapter(private val context: Context, private val clickListener: PeopleItemClickListener) : ListAdapter<PersonInfo, PeopleListItemAdapter.PeopleListViewHolder>(
    PeopleItemModelDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleListViewHolder {
        return PeopleListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PeopleListViewHolder, position: Int) {
        holder.bind(context, clickListener, getItem(position))
    }

    class PeopleListViewHolder(private val binding: LayoutPersonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, clickListener: PeopleItemClickListener, itemData: PersonInfo) {
            binding.itemClickListener = clickListener
            binding.person = itemData
            itemData.posterPath.let { Picasso.with(context).load("${GlobalConstants.SERVER_IMAGE_URL}${itemData.posterPath}").fit().into(binding.imagePerson)}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PeopleListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutPersonItemBinding.inflate(layoutInflater, parent, false)
                return PeopleListViewHolder(binding)
            }
        }
    }

    class PeopleItemModelDiffCallback : DiffUtil.ItemCallback<PersonInfo>() {
        override fun areItemsTheSame(oldItem: PersonInfo, newItem: PersonInfo) = (oldItem.id == newItem.id)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PersonInfo, newItem: PersonInfo) = (oldItem == newItem)
    }
}