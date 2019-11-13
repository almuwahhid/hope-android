package id.co.hope.app.artikel

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.R
import id.co.hope.app.artikel.adapter.ArtikelAdapter
import id.co.hope.app.artikel.model.ArtikelModel
import id.co.hope.app.main.home.HomePresenter
import id.co.hope.app.main.home.HomeView
import id.co.hope.module.Activity.HopeActivity
import kotlinx.android.synthetic.main.activity_artikel.*
import kotlinx.android.synthetic.main.layout_helper.*
import kotlinx.android.synthetic.main.main_toolbar.*

class ArtikelActivity : HopeActivity(), HomeView.View {
    override fun onGotoChecklist(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>) {

    }

    override fun onGotoSurvey(message_survey: String, message_button: String) {

    }

    override fun onFailed(message: String) {

    }

    override fun onCreateSurvey(surveyModel: SurveyModel) {

    }

    var artikels: MutableList<ArtikelModel> = ArrayList()
    lateinit var adapterArtikel: ArtikelAdapter
    lateinit var presenter: HomePresenter

    override fun onRequestArtikel(list: MutableList<ArtikelModel>) {
        this.artikels.clear()
        this.artikels.addAll(list)
        adapterArtikel.notifyDataSetChanged()
    }

    override fun onEmptyData(message: String) {
        helper_nodata.visibility = View.VISIBLE
    }

    override fun onErrorConnection() {
        helper_error.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        helper_loading.visibility = View.GONE
    }

    override fun onLoading() {
        helper_nodata.visibility = View.GONE
        helper_error.visibility = View.GONE
        helper_loading.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Artikel Karir")
        }

        adapterArtikel = ArtikelAdapter(context, artikels, object : ArtikelAdapter.OnAdapterArtikel{
            override fun onArtikelClick(model: ArtikelModel) {
                val httpIntent = Intent(Intent.ACTION_VIEW)
                httpIntent.data = Uri.parse(model.url_artikel)

                startActivity(httpIntent)
            }
        })
        presenter = HomePresenter(context, this)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapterArtikel

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            presenter.requestArtikel()
        }
        presenter.requestArtikel()
    }
}
