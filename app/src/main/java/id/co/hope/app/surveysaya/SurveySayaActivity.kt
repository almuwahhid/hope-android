package id.co.hope.app.surveysaya

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.R
import id.co.hope.app.surveysaya.adapter.SurveySayaAdapter
import id.co.hope.dialogs.DialogDetailAchievement.DialogDetailAchievement
import id.co.hope.module.Activity.HopeActivity
import kotlinx.android.synthetic.main.activity_survey_saya.*
import kotlinx.android.synthetic.main.layout_helper.*
import kotlinx.android.synthetic.main.main_toolbar.*

class SurveySayaActivity : HopeActivity(), SurveySayaView.View {

    var surveySayaModelList: MutableList<SurveyModel> = ArrayList()
    lateinit var adapterSurveySaya: SurveySayaAdapter
    lateinit var presenter: SurveySayaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_saya)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Riwayat Survey")
        }

        adapterSurveySaya = SurveySayaAdapter(context, surveySayaModelList, object : SurveySayaAdapter.OnSurveySayaAdapter{
            override fun onSurveyClick(model: SurveyModel) {
                DialogDetailAchievement(context, model!!)
            }
        })

        presenter = SurveySayaPresenter(context, this)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapterSurveySaya

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            presenter.requestSurveySaya()
        }
        presenter.requestSurveySaya()
    }

    override fun onRequestSurveySaya(surveySayaModelList: List<SurveyModel>) {
        this.surveySayaModelList.clear()
        this.surveySayaModelList.addAll(surveySayaModelList)
        adapterSurveySaya.notifyDataSetChanged()
    }

    override fun onErrorConnection() {
        helper_error.visibility = View.VISIBLE
    }

    override fun onEmptyData() {
        helper_nodata.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        helper_loading_full.visibility = View.GONE
    }

    override fun onLoading() {
        helper_nodata.visibility = View.GONE
        helper_error.visibility = View.GONE
        helper_loading_full.visibility = View.VISIBLE
    }
}
