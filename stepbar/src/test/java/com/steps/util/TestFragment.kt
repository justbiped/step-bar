package com.steps.util

import android.os.Bundle
import android.support.v4.app.Fragment
import repadm.com.repdm.ui.component.step.Step

class TestFragment : Fragment(), Step {
    override var value: Bundle
        get() {
            val bundle = Bundle()
            bundle.putString(getParamKey(), getParamValue())
            return bundle
        }
        set(value) {}

    private fun getParamKey(): String? = arguments?.getString("key")

    private fun getParamValue(): String? = arguments?.getString("value")

    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit) {
        invalidate.invoke(arguments?.getBoolean("isValid"))
    }

}