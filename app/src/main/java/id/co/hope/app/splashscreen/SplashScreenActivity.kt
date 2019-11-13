package id.co.hope.app.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import id.co.hope.R
import lib.almuwahhid.utils.LibUi
import id.co.hope.data.Preferences
import id.co.hope.app.greeting.GreetingActivity
import id.co.hope.app.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    internal var timer: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initTimer()
        timer!!.start()
        YoYo.with(Techniques.FadeIn)
            .duration(1000)
            .repeat(0)
            .playOn(img_logo)
    }

    private fun initTimer() {
        timer = object : Thread() {
            override fun run() {
                try {
                    //Create the database
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if(LibUi.getSPString(baseContext, Preferences.USER_ACCESS).equals("")){
                        startActivity(Intent(this@SplashScreenActivity, GreetingActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    }

                    finish()
                }
            }
        }
    }
}
