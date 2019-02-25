package com.steps.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.steps.Step
import com.steps.StepBar
import com.steps.stepbar.R

class TestActivity : AppCompatActivity() {
    private var stepIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val stepBar = findViewById<StepBar>(R.id.stepBar)


        val states = intent.getBooleanArrayExtra("states").asList()
        val steps = buildStepWithRespectiveState(states)

        viewPager.adapter = TestAdapter(steps, supportFragmentManager)
        stepBar.setViewPager(viewPager)
    }

    private fun buildStepWithRespectiveState(states: List<Boolean>): List<Step> {
        return states.map { buildTestFragment(it) }.toList()
    }

    private fun buildTestFragment(state: Boolean): Step {
        return TestStepFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isValid", state)
                putString("key", "step$stepIndex")
                putString("result", "step-${stepIndex++}")
            }
        }
    }

}