package com.laks.tvseries.featuresearch.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SearchTabViewPager : ViewPager {
    private var isSwipe = false
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setSwipe(isSwipe: Boolean) {
        this.isSwipe = isSwipe
    }
    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if ((this.isSwipe)) super.onInterceptTouchEvent(arg0) else false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.isSwipe) super.onTouchEvent(event) else false
    }
}