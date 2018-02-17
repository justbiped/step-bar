package repadm.com.repdm.ui.component.step

interface Step {
    fun invalidateStep(invalidate: (isValid: Boolean) -> Unit)
}