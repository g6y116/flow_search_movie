package com.flow.hugh.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flow.hugh.data.Movie
import com.flow.hugh.databinding.ItemMovieBinding

class MovieAdapter(private val viewHolderBindListener: ViewHolderBindListener):
    PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie) = old.title == new.title
            override fun areContentsTheSame(old: Movie, new: Movie) = old == new
        }
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding,
        private val viewHolderBindListener: ViewHolderBindListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.item = item
            Glide.with(binding.imageView).load(item.image).placeholder(ColorDrawable(Color.WHITE)).into(binding.imageView)
            viewHolderBindListener.onViewHolderBind(this, item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewHolderBindListener)
    }
}