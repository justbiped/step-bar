package com.steps.util

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.view.View

class StepStyleableManager constructor(private var typedArray: TypedArray) {


    fun setup(styleableId: Int, vararg views: View) {
        views.forEach { setViewBackGroundTInt(styleableId, it) }
    }

    private fun setViewBackGroundTInt(styleableId: Int, view: View) {
        val colorStateList = typedArray.getColorStateList(styleableId)

        if (colorStateList != null && colorStateList.isStateful) {
            view.backgroundTintList = colorStateList
            view.backgroundTintList = colorStateList
        } else {
            val states = arrayOfStates()
            val colors = arrayOfColor(colorStateList)
            val newColorStateList = ColorStateList(states, colors)
            view.backgroundTintList = newColorStateList
            view.backgroundTintList = newColorStateList
        }
    }

    private fun arrayOfColor(colorStateList: ColorStateList) =
            intArrayOf(colorStateList.defaultColor, Color.LTGRAY, colorStateList.defaultColor)

    private fun arrayOfStates() =
            arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled), intArrayOf())
}