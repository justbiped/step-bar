package steps.com.sampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.step_container_activity.*
import repadm.com.repdm.ui.component.step.Step


class StepContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_container_activity)

        initViews()
    }

    private fun initViews() {
        val steps = listOf<Step>(FirstStepFragment(), SecondStepFragment())

        viewPager.adapter = SampleStepAdapter(steps, supportFragmentManager)
        stepBar.setViewPager(viewPager)

        stepBar.setOnCompleteListener(::startResultActivity)
    }

    private fun startResultActivity(bundle: Bundle) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtras(bundle)

        startActivity(intent)
    }
}