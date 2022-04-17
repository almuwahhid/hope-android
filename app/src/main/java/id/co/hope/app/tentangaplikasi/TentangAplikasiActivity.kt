package id.co.hope.app.tentangaplikasi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.co.hope.R
import id.co.hope.module.Activity.HopeActivity
import kotlinx.android.synthetic.main.main_toolbar.*

class TentangAplikasiActivity : HopeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_aplikasi)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Tentang Aplikasi")
        }
    }
}
