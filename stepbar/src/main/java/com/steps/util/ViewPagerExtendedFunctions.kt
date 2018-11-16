package com.steps.util

import androidx.viewpager.widget.ViewPager

fun androidx.viewpager.widget.ViewPager.onPageChange(pageSelected: (page: Int) -> Unit) {
    this.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            pageSelected(position)
        }

    })
}