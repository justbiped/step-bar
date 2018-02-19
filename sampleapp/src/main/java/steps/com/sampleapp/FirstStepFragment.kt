package steps.com.sampleapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.first_step_fragment.*
import repadm.com.repdm.ui.component.step.Step

class FirstStepFragment : Fragment(), Step {

    private lateinit var invalidate: (isValid: Boolean) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.first_step_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validate()
            }

        })
    }

    private fun validate() {
        invalidate.invoke(editText != null
                && !editText.text.isEmpty())
    }

    override fun invalidateStep(invalidate: (isValid: Boolean) -> Unit) {
        this.invalidate = invalidate
        validate()
    }

}