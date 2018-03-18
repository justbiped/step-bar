# Step Bar

With this library you can have a step bar that allows you to manage multiple fragment steps in a simple way.

[![](https://jitpack.io/v/roubertedgar/step-bar.svg)](https://jitpack.io/#roubertedgar/step-bar)

[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Simple%20Step%20Bar-green.svg?style=flat )]( https://android-arsenal.com/details/1/6848 )

----------

![Alt Text](https://media.giphy.com/media/nbOPo5sSZiEmM8YjTc/giphy.gif)

### Add ViewPager and StepBar on layout

The step bar is a viewGroup, so, you can put a close tag like
< /StepBar>
and put some other view inside it.

  ```xml
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
  ```

### Implementation of view
Here you have to give a view pager to the step bar.  After complete all steps, the step bar will call the  onComplete of your onCompleteListener. It gives you a bundle with all values of your steps. You can see an simple implementation of it below:

```kotlin
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
  ```

### The Fragment
  All the step fragments has to Extends support.v4.app.Fragment and implements Step:

  ```java
  class FirstStepFragment : Fragment(), Step {
  ```

with this, you have to provide a implementation to var:Bundle, like this:

  ```kotlin
    override var value: Bundle
    get() = getValues()
    set(value) {}

    private fun getValues(): Bundle {
        val bundle = Bundle()
        bundle.putString("keyStepExample", "some value")
        return bundle
    }
```

And to implements invalidateStep method to, like:

```kotlin
    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit{
        invalidate.invoke(true) //this step will be ever valid
     }
```

For example, if you want to validate the step checking if some edit text is empty or not, you could do this:

```kotlin
    override fun invalidateStep(invalidate: (isValid: Boolean?) -> Unit) {
        this.invalidate = invalidate

        validate() //This is for validate when step back, it keeps the previous valid step valid
    }

    private fun validate() = invalidate(editText?.text?.let { !it.isEmpty() })
```

### StepAdapter
To work with step bar, your adapter has to extends the StepAdapter and implements getCount and getStep

```kotlin
    override fun getCount(): Int = steps.size
```
```kotlin
    override fun getStep(position: Int): Step = steps[position]
```

### Customize
For now you can customize 2 attributes of step bar.

First, import custom attributes
```xml
 xmlns:step="http://schemas.android.com/apk/res-auto"
 ```

***buttons_tint***: this attribute changes the drawable color of back and next step buttons

```xml
<com.steps.StepBar
      android:id="@+id/stepBar"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignParentBottom="true"
      step:buttons_tint="@color/colorAccent"/>
```

***done_text_tint***: Using this, you can customize the color of done button text
```xml
<com.steps.StepBar
        android:id="@+id/stepBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        step:done_text_tint="@color/colorPrimary"/>
```


if you don't customize the button text tint, the tint of done text will be the same of back and next step buttons
