# Step Bar

With this library you can have a step bar that allows you to manage multiple fragment steps in a simple way. 


----------


**Add ViewPager and StepBar on layout**
The step bar is a viewGroup, so, you can put a close tag like \</StepBar>
and put some other view inside it.

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/stepBar" />
    
    <com.steps.StepBar
        android:id="@+id/stepBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true" />


**Implementation of view**  
Here you have to give a view pager to the step bar.  After complete all steps, the step bar will call the  onComplete of your onCompleteListener. It gives you a bundle with all values of your steps. You can see an simple implementation of it below:

     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.step_container_activity)
    
            val steps = listOf<Step>(FirstStepFragment(), SecondStepFragment())
    
            viewPager.adapter = SampleStepAdapter(steps, supportFragmentManager)
            stepBar.setViewPager(viewPager)
    
		    //After compleet the stpes, onComplete will be called
	        stepBar.setOnCompleteListener({
				val intent = Intent(this, ResultActivity::class.java)
	            intent.putExtras(bundle)
	            startActivity(intent)
            })
        }
**The Fragment**
    All the step fragments has to Extends support.v4.app.Fragment and implements Step:  

    class FirstStepFragment : Fragment(), Step {
with this, you have to provide a implementation to var:Bundle, like this:

    override var value: Bundle
    get() = getValues()
    set(value) {}
    
    private fun getValues(): Bundle {
        val bundle = Bundle()
        bundle.putString("keyStepExample", "some value")
        return bundle
    }

And to implements invalidateStep method to, like:

    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit{
        invalidate.invoke(true) //this step will be ever valid
     }
For example, if you want to validate the step checking if some edit text is empty or not, you could do this:
    
    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit) {
        this.invalidate = invalidate
		/*This is for validate when step back, it keeps the previus valid step valid
        validate() 
    }
    
    private fun validate() = invalidate.invoke(editText?.text?.let { !it.isEmpty() })

**StepAdapter**
To work with step bar, your adapter has to extends the StepAdapter and implements getCount and getStep

    override fun getCount(): Int = steps.size

    override fun getStep(position: Int): Step = steps[position]

        
        


<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA2NDQzODEzM119
-->