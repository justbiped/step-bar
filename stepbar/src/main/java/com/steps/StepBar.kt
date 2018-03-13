package com.steps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.steps.util.StepStyleableManager

class StepBar(context: Context, private var attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    private var onComplete: (Bundle) -> Unit = {}

    private lateinit var nextStep: Button
    private lateinit var backStep: Button
    private lateinit var doneStep: Button

    private lateinit var viewPager: ViewPager

    init {
        val view = View.inflate(context, R.layout.step_bar, this)
        initViews(view)
    }

    private fun initViews(view: View) {
        nextStep = view.findViewById(R.id.nextStep)
        backStep = view.findViewById(R.id.backStep)
        doneStep = view.findViewById(R.id.doneStep)

        setupStyleable()
    }

    private fun setupStyleable() {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepBar)
        var styleable = StepStyleableManager(typedArray)

        styleable.setup(R.styleable.StepBar_buttons_tint, nextStep, backStep, doneStep)
        styleable.setup(R.styleable.StepBar_done_text_tint, doneStep)

        typedArray.recycle()
    }

    fun setViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        onSetViewPager()
    }

    fun setOnCompleteListener(onComplete: (Bundle) -> Unit) {
        this.onComplete = onComplete
    }

    private fun onSetViewPager() {
        configureStepButtons(viewPager.currentItem)

        disableViewPagerScroll()
        setOnViewPagerChangeListener()
        addButtonStepsListeners()
    }

    private fun configureStepButtons(stepPosition: Int) {
        backStep.isEnabled = isNotFirstStep(stepPosition)
        validateCurrentStep(stepPosition)

        switchNextButtonDrawable(isLastStep(stepPosition))
    }

    private fun validateCurrentStep(stepPosition: Int) {
        val stepAdapter = (viewPager.adapter as StepAdapter)
        val currentStep = stepAdapter.getStep(stepPosition)

        currentStep.invalidateStep {
            nextStep.isEnabled = it ?: false
            doneStep.isEnabled = it ?: false
        }
    }

    private fun switchNextButtonDrawable(isLastStep: Boolean) {
        if (isLastStep) {
            nextStep.visibility = View.INVISIBLE
            doneStep.visibility = View.VISIBLE
        } else {
            nextStep.visibility = View.VISIBLE
            doneStep.visibility = View.INVISIBLE
        }
    }

    private fun addButtonStepsListeners() {
        backStep.setOnClickListener { increaseStep(-1) }
        nextStep.setOnClickListener { toNextStep() }
        doneStep.setOnClickListener { onComplete.invoke(processSteps()) }
    }

    private fun toNextStep() {
        increaseStep(1)
    }

    private fun increaseStep(value: Int) {
        viewPager.setCurrentItem(viewPager.currentItem + value, true)
    }

    private fun processSteps(): Bundle {
        val stepAdapter = viewPager.adapter as StepAdapter
        val resultBundle = Bundle()

        for (i in 0 until stepAdapter.count) {
            resultBundle.putAll(stepAdapter.getStep(i).value)
        }
        return resultBundle
    }

    private fun isNotFirstStep(stepPosition: Int) = stepPosition != 0

    private fun isLastStep(stepNumber: Int) = stepNumber + 1 == viewPager.adapter?.count

    private fun setOnViewPagerChangeListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                configureStepButtons(position)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableViewPagerScroll() {
        viewPager.setOnTouchListener { _, _ -> true }
    }
}
