package steps.com.stepbar.util

import android.support.v4.app.Fragment
import repadm.com.repdm.ui.component.step.Step

class TestFragment : Fragment(), Step {

    override fun invalidateStep(invalidate: (isValid: Boolean) -> Unit) {
        invalidate.invoke(arguments.getBoolean("isValid"))
    }
}