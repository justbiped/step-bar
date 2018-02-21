package steps.com.sampleapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.step_container_activity.*
import repadm.com.repdm.ui.component.step.Step


class StepContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_container_activity)

        initViews()
    }

    private fun initViews() {
        val steps = listOf<Fragment>(FirstStepFragment(), SecondStepFragment())

        viewPager.adapter = SampleStepAdapter(steps, supportFragmentManager)
        stepBar.setViewPager(viewPager)

        stepBar.setOnCompleteListener { processSteps(steps)}
    }

    private fun processSteps(steps: List<Fragment>) {
        val bundle = Bundle()
        steps.forEach { bundle.putAll((it as Step).value) }
        print(bundle.toString())
    }
}