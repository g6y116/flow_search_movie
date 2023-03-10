package com.flow.hugh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flow.hugh.databinding.ItemRecentBinding

class RecentAdapter(private val viewHolderBindListener: ViewHolderBindListener):
    ListAdapter<String, RecentAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(old: String, new: String) = old == new
            override fun areContentsTheSame(old: String, new: String) = old == new
        }
    }

    inner class ViewHolder(
        private val binding: ItemRecentBinding,
        private val viewHolderBindListener: ViewHolderBindListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            viewHolderBindListener.onViewHolderBind(this, item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewHolderBindListener)
    }
}