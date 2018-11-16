package steps.com.sampleapp

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StepContainerFunctionalTest {

    @get:Rule
    val activityRule = ActivityTestRule(StepContainer::class.java)

    @Test
    fun goToSecondStepWhenFirstStepIsValid() {
        writeTo(R.id.stepEditText, "Step1")

        clickOn(R.id.nextStep)

        assertDisplayed(R.id.textViewStep2)
    }

    @Test
    fun stayInFirstStepWhenIsInvalid() {
        assertDisplayed(R.id.textViewStep1)

        clickOn(R.id.nextStep)

        assertDisplayed(R.id.textViewStep1)
    }

    @Test
    fun seeResultScreenWhenAllStepIsValid() {
        writeTo(R.id.stepEditText, "Step1")
        clickOn(R.id.nextStep)

        writeTo(R.id.stepEditText2, "Step2")
        clickOn(R.id.doneStep)

        assertDisplayed("Step1")
        assertDisplayed("Step2")
    }
}