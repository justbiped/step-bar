package com.steps

import android.os.Bundle

interface Step {
    var value: Bundle

    fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit)
}