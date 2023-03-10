package com.flow.hugh.adapter

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic @BindingAdapter("html_text")
    fun setHtmlText(view: TextView, text: String?) {
        text?.let {
            view.text = Html.fromHtml(text)
        }
    }
}