package com.flow.hugh

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import com.orhanobut.logger.Logger

fun View.onClick(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}

class OnSingleClickListener(
    private var interval: Int = 250,
    private var onSingleClick: (v: View) -> Unit
) : View.OnClickListener {
    private var lastClickTime: Long = 0
    override fun onClick(v: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if ((elapsedRealtime - lastClickTime) < interval) return
        lastClickTime = elapsedRealtime
        onSingleClick(v)
    }
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun log(message: String) {
    Logger.d(message)
}

fun main() {
    //
}