package com.example.news.helper

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class CustomWebView : WebView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        privateBrowsing: Boolean
    ) : super(context, attrs, defStyleAttr, privateBrowsing) {
    }

    val maximumScrollValue: Int
        get() = computeVerticalScrollRange() - height

    fun smoothScrollTo(yAxis: Int) {
        val anim = ObjectAnimator.ofInt(
            this, "scrollY",
            this.scrollY, yAxis
        )
        anim.setDuration(500).start()
    }
}