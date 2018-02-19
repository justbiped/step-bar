package steps.com.sampleapp

import android.support.v4.app.Fragment
import repadm.com.repdm.ui.component.step.Step

class SecondStepFragment : Fragment(), Step {

    override fun invalidateStep(invalidate: (isValid: Boolean) -> Unit) {
    }

}