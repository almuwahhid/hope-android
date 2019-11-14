package id.co.hope.app.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.R
import id.co.hope.app.artikel.adapter.ArtikelAdapter
import id.co.hope.app.artikel.model.ArtikelModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_shimmer_article.*
import lib.almuwahhid.Activity.FragmentPermission
import id.co.hope.app.artikel.ArtikelActivity
import id.co.hope.app.main.MainViewModel
import id.co.hope.app.survey.SurveyActivity
import lib.almuwahhid.utils.LibUi
import java.util.Observer
import id.co.hope.utils.HopeFunction

class HomeFragment : FragmentPermission(), HomeView.View {

    var artikels: MutableList<ArtikelModel> = ArrayList()
    lateinit var adapterArtikel: ArtikelAdapter
    lateinit var presenter: HomePresenter
    lateinit var viewModel: MainViewModel


    override fun onFailed(message: String) {

    }

    override fun onEmptyData(message: String) {
        tv_nodata.visibility = View.VISIBLE
        tv_selengkapnya.visibility = View.GONE
    }

    override fun onErrorConnection() {

    }

    override fun onHideLoading() {
        shimer_artikel.stopShimmer()
        shimer_artikel.visibility = View.GONE
    }

    override fun onLoading() {
        shimer_artikel.startShimmer()
        shimer_artikel.visibility = View.VISIBLE
        tv_nodata.visibility = View.GONE
    }

    override fun onRequestArtikel(list: MutableList<ArtikelModel>) {
        this.artikels.clear()

         if(list.size > 4){
             for (i in 0..3) {
                 this.artikels.add(list.get(i))
             }
            tv_selengkapnya.visibility = View.VISIBLE
        } else {
             this.artikels.addAll(list)
             tv_selengkapnya.visibility = View.GONE
         }
        adapterArtikel.notifyDataSetChanged()
    }

    companion object{
        fun newInstance(mainViewModel: MainViewModel): HomeFragment {
            val fragment = HomeFragment()
            fragment.viewModel = mainViewModel
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initStartSurvey().observe(this, android.arch.lifecycle.Observer {
            if(it!!){
                introStartSurvey()
            }
        })

        adapterArtikel = ArtikelAdapter(context!!, artikels, object : ArtikelAdapter.OnAdapterArtikel{
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

        tv_selengkapnya.setOnClickListener({
            startActivity(Intent(context, ArtikelActivity::class.java))
        })
        btn_survey.setOnClickListener({
            presenter.createSurvey()
        })

    }

    private fun introStartSurvey(){
        HopeFunction.showIntroCase(activity,
            btn_survey,
            "Memulai Survey",
            "Kamu bisa memulai survey dengan menekan tombol ini",
            true,
            object : LibUi.OnEventChange {
                override fun onChange() {

                }
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onGotoChecklist(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>) {
        tv_description.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n\nAnda memiliki list jurnal yang harus dikerjakan, yuk lihat sekarang...")
        btn_survey.setText("Lihat Checklist Journal")
        btn_survey.setOnClickListener({
            viewModel.updateChecklistJournal(true)
        })
    }

    override fun onGotoSurvey(message_survey: String, message_button : String) {
        tv_description.setText(resources.getString(R.string.home_description))
        btn_survey.setOnClickListener({
            presenter.createSurvey()
        })
        btn_survey.setText("M E N G E N A L   I M P I A N M U")
    }

    override fun onCreateSurvey(surveyModel: SurveyModel) {
        startActivity(Intent(context, SurveyActivity::class.java).putExtra("data", surveyModel))
    }

    override fun onResume() {
        super.onResume()
        presenter.checkSurvey()
    }
}
