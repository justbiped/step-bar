package steps.com.sampleapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SampleStepAdapter constructor(private val steps: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = steps.size

    override fun getItem(position: Int): Fragment = steps[position]

}