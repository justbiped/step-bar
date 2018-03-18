package steps.com.sampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.steps.Step
import kotlinx.android.synthetic.main.step_container_activity.*


class StepContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_container_activity)

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