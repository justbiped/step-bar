package com.steps


import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import org.assertj.android.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import com.steps.util.TestFragment
import com.steps.util.TestStepAdapter

@RunWith(RobolectricTestRunner::class)
class StepBarTest {
    private lateinit var stepBar: StepBar
    private lateinit var viewPager: ViewPager
    private lateinit var fragmentManager: FragmentManager

    private lateinit var nextStep: Button
    private lateinit var backStep: Button

    @Before
    fun setUp() {
        val activity = Robolectric.buildActivity(AppCompatActivity::class.java).get()

        fragmentManager = activity.supportFragmentManager
        viewPager = ViewPager(activity)
        stepBar = StepBar(activity)

        nextStep = stepBar.findViewById(R.id.nextStep)
        backStep = stepBar.findViewById(R.id.backStep)
    }

    @Test
    fun startsComponentWithStepButtonsDisabled() {
        assertEquals(viewPager.currentItem, 0)
        assertThat(backStep).isDisabled
        assertThat(nextStep).isDisabled
    }

    @Test
    fun validateCurrentStepOnSetViewPager() {
        viewPager.adapter = stepAdapter(validStep("", ""))
        assertThat(nextStep).isDisabled

        stepBar.setViewPager(viewPager)

        assertThat(nextStep).isEnabled
    }

    @Test
    fun validateCurrentStepAfterGoToNextPage() {
        viewPager.adapter = stepAdapter(validStep("", ""), invalidStep())
        stepBar.setViewPager(viewPager)
        assertThat(nextStep).isEnabled

        nextStep.performClick()

        assertEquals(viewPager.currentItem, 1)
        assertThat(nextStep).isDisabled
    }

    @Test
    fun receiveBundleWithAllStepValuesOnStepsComplete() {
        viewPager.adapter = stepAdapter(validStep("step1", "value 1"), validStep("step2", "value 2"))
        viewPager.currentItem = 1

        nextStep.performClick()

        stepBar.setOnCompleteListener {
            assertEquals("value 1", it.getString("step1"))
            assertEquals("value 2", it.getString("step2"))
        }
    }

    private fun stepAdapter(vararg steps: TestFragment) = TestStepAdapter(steps.toList(), fragmentManager)

    private fun invalidStep(): TestFragment {
        val fragment = TestFragment()
        val bundle = Bundle()
        bundle.putBoolean("isValid", false)

        fragment.arguments = bundle
        return fragment
    }

    private fun validStep(key: String, value: String): TestFragment {
        val fragment = TestFragment()
        val bundle = Bundle()
        bundle.putBoolean("isValid", true)
        bundle.putString(key, value)

        fragment.arguments = bundle
        return fragment
    }
}
