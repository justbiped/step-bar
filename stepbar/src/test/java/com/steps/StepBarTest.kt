package com.steps

import android.os.Bundle
import android.support.v4.view.ViewPager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import android.view.LayoutInflater
import android.widget.Button
import com.steps.util.TestAdapter
import com.steps.util.TestStepFragment
import org.assertj.android.api.Assertions.assertThat
import org.robolectric.Robolectric
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager


@RunWith(RobolectricTestRunner::class)
class StepBarTest {
    private lateinit var stepBar: StepBar

    private lateinit var nextStep: Button
    private lateinit var backStep: Button
    private lateinit var doneButton: Button

    @Before
    fun setUp() {
        stepBar = inflateStepBar()

        nextStep = stepBar.findViewById(R.id.nextStep)
        backStep = stepBar.findViewById(R.id.backStep)
        doneButton = stepBar.findViewById(R.id.doneStep)
    }

    @Test
    fun disabledDefaultStateForNextStepButton() {
        assertThat(nextStep).isDisabled
    }

    @Test
    fun disabledDefaultStateForBackStepButton() {
        assertThat(backStep).isDisabled
    }


    @Test
    fun invisibleDefaultStateForDoneButton() {
        assertThat(doneButton).isInvisible
    }

    @Test
    fun enableNextButtonWhenStepIsValid() {
        val viewPager = ViewPager(RuntimeEnvironment.application.baseContext)

        viewPager.adapter = getAdapter(step(isValid = true), step(isValid = false))
        stepBar.setViewPager(viewPager)

        assertThat(nextStep).isEnabled
    }

    @Test
    fun turnDoneButtonVisibleOnLastStep() {
        val viewPager = ViewPager(RuntimeEnvironment.application.baseContext)

        viewPager.adapter = getAdapter(step(isValid = true), step(isValid = false))
        stepBar.setViewPager(viewPager)
        nextStep.performClick()

        assertThat(nextStep).isInvisible
        assertThat(doneButton).isVisible
    }

    private fun inflateStepBar(): StepBar {
        return LayoutInflater
                .from(RuntimeEnvironment.application)
                .inflate(R.layout.step_bar,
                        StepBar(RuntimeEnvironment.application, Robolectric.buildAttributeSet().build()),
                        true) as StepBar
    }

    private fun getAdapter(vararg steps: Step): StepAdapter {
        return TestAdapter(steps.toList(), fragmentManager())
    }

    private fun step(isValid: Boolean): Step {
        val step = TestStepFragment()
        val bundle = Bundle()

        bundle.putBoolean("isValid", isValid)

        step.arguments = bundle
        return step
    }

    private fun fragmentManager(): FragmentManager {
        return Robolectric
                .buildActivity(FragmentActivity::class.java)
                .create()
                .get().supportFragmentManager
    }
}

