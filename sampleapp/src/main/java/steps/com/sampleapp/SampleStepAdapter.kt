package steps.com.sampleapp

import com.steps.Step
import com.steps.StepAdapter

class SampleStepAdapter constructor(private val steps: List<Step>, fm: androidx.fragment.app.FragmentManager) : StepAdapter(fm) {

    override fun getCount(): Int = steps.size

    override fun getStep(position: Int): Step = steps[position]
}