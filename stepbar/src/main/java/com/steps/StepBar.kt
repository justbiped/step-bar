package com.steps

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import repadm.com.repdm.ui.component.step.Step

class StepBar : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private lateinit var nextStep: Button
    private lateinit var backStep: Button
    private lateinit var viewPager: ViewPager
    private lateinit var onComplete: () -> Unit

    init {
        val view = View.inflate(context, R.layout.step_bar, this)
        initViews(view)
    }

    @SuppressLint("WrongViewCast")
    private fun initViews(view: View) {
        nextStep = view.findViewById(R.id.nextStep)
        backStep = view.findViewById(R.id.backStep)
    }

    fun setViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        onSetViewPager()
    }

    fun setOnCompleteListener(onComplete: () -> Unit) {
        this.onComplete = onComplete
    }

    private fun onSetViewPager() {
        configureStep(viewPager.currentItem)

        disableViewPagerScroll()
        setOnViewPagerChangeListener()
        addButtonStepsListeners()
    }

    private fun configureStep(stepPosition: Int) {
        backStep.isEnabled = isNotFirstStep(stepPosition)
        validateNextStepButton(stepPosition)

        switchNextButtonDrawable(stepPosition)
    }

    private fun validateNextStepButton(stepPosition: Int) {
        val stepAdapter = (viewPager.adapter as FragmentPagerAdapter)
        val currentStep = (stepAdapter.getItem(stepPosition) as Step)

        currentStep.invalidateStep { nextStep.isEnabled = it }
    }

    private fun switchNextButtonDrawable(stepPosition: Int) {
        if (isLastStep(stepPosition)) {
            nextStep.background = context.getDrawable(R.drawable.button_step_done_states)
        } else {
            nextStep.background = context.getDrawable(R.drawable.button_step_next_states)
        }
    }

    private fun addButtonStepsListeners() {
        nextStep.setOnClickListener { toNextStep() }
        backStep.setOnClickListener { increaseStep(-1) }
    }

    private fun toNextStep() {
        if (isLastStep(viewPager.currentItem)) onComplete.invoke()
        else increaseStep(1)
    }

    private fun increaseStep(value: Int) {
        viewPager.setCurrentItem(viewPager.currentItem + value, true)
    }

    private fun isNotFirstStep(stepPosition: Int) = stepPosition != 0

    private fun isLastStep(stepNumber: Int) = stepNumber + 1 == viewPager.adapter?.count

    private fun setOnViewPagerChangeListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                configureStep(position)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableViewPagerScroll() {
        viewPager.setOnTouchListener { _, _ -> true }
    }
}
