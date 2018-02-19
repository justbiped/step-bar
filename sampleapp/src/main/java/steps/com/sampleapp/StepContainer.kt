package steps.com.sampleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.step_container_activity.*


class StepContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_container_activity)

        initViews()
    }

    private fun initViews() {
        viewPager.adapter = SampleStepAdapter(listOf(FirstStepFragment(), SecondStepFragment()), supportFragmentManager)
        stepBar.setViewPager(viewPager)
    }
}