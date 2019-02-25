package com.steps.util

import androidx.fragment.app.FragmentManager
import com.steps.Step
import com.steps.StepAdapter

class TestAdapter constructor(private val steps: List<Step>, fm: FragmentManager) : StepAdapter(fm) {

    override fun getStep(position: Int): Step = steps[position]

    override fun getCount(): Int = steps.size

}