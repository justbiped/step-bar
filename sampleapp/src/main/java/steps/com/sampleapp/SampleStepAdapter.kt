package steps.com.sampleapp

import android.support.v4.app.FragmentManager
import com.steps.StepAdapter
import repadm.com.repdm.ui.component.step.Step

class SampleStepAdapter constructor(private val steps: List<Step>, fm: FragmentManager) : StepAdapter(fm) {

    override fun getCount(): Int = steps.size

    override fun getStep(position: Int): Step = steps[position]
}