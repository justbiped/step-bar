package com.steps

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import repadm.com.repdm.ui.component.step.Step

abstract class StepAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = getStep(position) as Fragment

    abstract fun getStep(position: Int): Step
}