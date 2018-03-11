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

    private lateinit var viewPager: ViewPager
    private lateinit var nextStepDrawable: Drawable

    init {
        val view = View.inflate(context, R.layout.step_bar, this)
        initViews(view)
    }

    private fun initViews(view: View) {
        nextStep = view.findViewById(R.id.nextStep)
        backStep = view.findViewById(R.id.backStep)

        setupStyleable()
    }

    private fun setupStyleable() {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepBar)
        val styleable = StepStyleableManager(typedArray)

        styleable.setup(R.styleable.StepBar_buttons_tint, nextStep, backStep)
        nextStepDrawable = nextStep.compoundDrawables[2]

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

        currentStep.invalidateStep { nextStep.isEnabled = it ?: false }
    }

    private fun switchNextButtonDrawable(isLastStep: Boolean) {
        if (isLastStep) {
            nextStep.text = context.getString(R.string.done)
            nextStep.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else {
            nextStep.text = ""
            nextStep.setCompoundDrawablesWithIntrinsicBounds(null, null, nextStepDrawable, null)
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
