package com.flow.hugh.adapter

import androidx.recyclerview.widget.RecyclerView

interface ViewHolderBindListener {
    fun onViewHolderBind(holder: RecyclerView.ViewHolder, item: Any)
}