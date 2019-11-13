package id.co.hope.app.fotopreview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.co.hope.R
import id.co.hope.module.Activity.HopeActivity
import kotlinx.android.synthetic.main.activity_foto_preview.*
import kotlinx.android.synthetic.main.toolbar_transparent.*

class FotoPreviewActivity : HopeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto_preview)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("")
        }

        val data = intent.extras

        if (data == null)
            finish()

        Picasso.with(context)
            .load(data.getString("image"))
            .placeholder(R.drawable.ic_hope)
            .into(img_preview, object : Callback {
                override fun onError() {

                }
                override fun onSuccess() {
                    progress_bar.setVisibility(View.GONE)
                }

                fun onError(e: Exception) {
                    progress_bar.setVisibility(View.GONE)

                }
            })
        img_preview
    }
}
