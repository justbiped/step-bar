package com.steps.util

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.widget.Button

class StepStyleableManager constructor(var typedArray: TypedArray) {


    fun setup(styleableId: Int, vararg buttons: Button) {
        buttons.forEach { setViewBackGroundTInt(styleableId, it) }
    }

    private fun setViewBackGroundTInt(styleableId: Int, button: Button) {
        val colorStateList = typedArray.getColorStateList(styleableId)

        if (colorStateList != null) {
            if (colorStateList.isStateful) {
                button.backgroundTintList = colorStateList
                button.backgroundTintList = colorStateList
                button.setTextColor(colorStateList)
                button.compoundDrawables.forEach { it?.also { it.setTintList(colorStateList) } }
            } else {
                val states = arrayOfStates()
                val colors = arrayOfColor(colorStateList)
                val newColorStateList = ColorStateList(states, colors)
                button.backgroundTintList = newColorStateList
                button.backgroundTintList = newColorStateList
                button.setTextColor(newColorStateList)
                button.compoundDrawables.forEach { it?.also { it.setTintList(newColorStateList) } }
            }
        }
    }

    private fun arrayOfColor(colorStateList: ColorStateList) =
            intArrayOf(colorStateList.defaultColor, Color.LTGRAY, colorStateList.defaultColor)

    private fun arrayOfStates() =
            arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled), intArrayOf())
}