package com.steps.util

import android.support.v4.app.FragmentManager
import com.steps.StepAdapter
import repadm.com.repdm.ui.component.step.Step

class TestStepAdapter constructor(val fragments: List<Step>, fm: FragmentManager) : StepAdapter(fm) {

    override fun getStep(position: Int): Step = fragments[position]

    override fun getCount(): Int = fragments.size
}