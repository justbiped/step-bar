package repadm.com.repdm.ui.component.step

import android.os.Bundle

interface Step {
    var value: Bundle

    fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit)
}