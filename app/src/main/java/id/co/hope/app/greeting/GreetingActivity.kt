package id.co.hope.app.greeting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.co.hope.R
import id.co.hope.app.greeting.helper.GreetingHelper
import id.co.hope.app.login.LoginActivity
import id.co.hope.app.register.RegisterActivity
import id.co.hope.module.Activity.HopeActivity
import id.co.hope.utils.slider.SliderLayout
import id.co.hope.utils.slider.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.activity_greeting.*

class GreetingActivity : HopeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)



        for (x in GreetingHelper.greetingData()){
            slider.addSlider(TextSliderView(context, x.title, x.description))
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Default)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
//        slider.setCustomAnimation(ChildAnimation())
//        slider.animation =
        slider.setDuration(3000)

        btn_login.setOnClickListener({
            startActivity(Intent(context, LoginActivity::class.java))
        })

        btn_register.setOnClickListener({
            startActivity(Intent(context, RegisterActivity::class.java))
        })
    }
}
