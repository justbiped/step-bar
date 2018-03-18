package com.steps.util

import android.os.Bundle
import android.support.v4.app.Fragment
import com.steps.Step

class TestStepFragment : Fragment(), Step {

    override var value: Bundle
        get() = getResult()
        set(value) {}

    private fun getResult(): Bundle {
        val bundle = Bundle()
        bundle.putString(arguments?.getString("key"), arguments?.getString("result"))

        return bundle
    }

    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit) {
        invalidate(arguments?.getBoolean("isValid"))
    }

}