package com.steps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout

class StepBar : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private lateinit var nextStep: Button
    private lateinit var backStep: Button
    private lateinit var viewPager: ViewPager
    private var onComplete: (Bundle) -> Unit = {}

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

    fun setOnCompleteListener(onComplete: (Bundle) -> Unit) {
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
        val stepAdapter = (viewPager.adapter as StepAdapter)
        val currentStep = stepAdapter.getStep(stepPosition)

        currentStep.invalidateStep { nextStep.isEnabled = it ?: false }
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
        if (isLastStep(viewPager.currentItem)) onComplete.invoke(processSteps())
        else increaseStep(1)
    }

    private fun processSteps(): Bundle {
        val stepAdapter = viewPager.adapter as StepAdapter
        val resultBundle = Bundle()

        for (i in 0 until stepAdapter.count) {
            resultBundle.putAll(stepAdapter.getStep(i).value)
        }
        return resultBundle
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
