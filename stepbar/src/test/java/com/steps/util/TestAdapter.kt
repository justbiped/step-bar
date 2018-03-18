package com.steps.util

import android.support.v4.app.FragmentManager
import com.steps.Step
import com.steps.StepAdapter

class TestAdapter constructor(val steps: List<Step>, fm: FragmentManager) : StepAdapter(fm) {

    override fun getStep(position: Int): Step = steps[position]

    override fun getCount(): Int = steps.size

}