package id.co.hope.app.surveysaya

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.R
import id.co.hope.app.surveysaya.adapter.SurveySayaAdapter
import id.co.hope.dialogs.DialogDetailAchievement.DialogDetailAchievement
import id.co.hope.module.Activity.HopeActivity
import id.co.hope.utils.HopeFunction
import kotlinx.android.synthetic.main.activity_survey_saya.*
import kotlinx.android.synthetic.main.layout_helper.*
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.utils.LibUi
import lib.almuwahhid.utils.downloader.Exception.UiLibDownloadException
import lib.almuwahhid.utils.downloader.UiLibDownloadCallback
import lib.almuwahhid.utils.downloader.UiLibDownloadManager
import lib.almuwahhid.utils.downloader.UiLibDownloadRequest

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.download_survey, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val destination = Uri.withAppendedPath(
            Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)),
            "Hope"
        )
        val url = "http://hope.tutorial-sourcecode.com/laporan/laporansurvey/"+HopeFunction.getUserPreference(context).id_user
        when(item.itemId){
            R.id.nav_download->{
                try {
                    UiLibDownloadManager(context, object : UiLibDownloadCallback {
                        override fun onProcess(request: UiLibDownloadRequest) {
                            //                            onChatRoomClick.onDownloadingAttachment(true, position);
                            LibUi.ToastShort(context, "Sedang mendownload dokumen")
                            LibUi.showLoadingDialog(context, R.drawable.downloadez)

                        }

                        override fun onCancel(request: UiLibDownloadRequest) {

                        }

                        override fun onSuccess(request: UiLibDownloadRequest) {
                            LibUi.hideLoadingDialog(context)
                            LibUi.ToastShort(context, "Sukses mendownload dokumen")
                            //                            onChatRoomClick.onDownloadingAttachment(false, position);
                            //                                        onChatRoomClick.onClickAttachment(chat, request.getDestinationUri());
                        }
                    }).startRequest(UiLibDownloadRequest(Uri.parse(url)).setDestinationUri(destination))
                } catch (e: UiLibDownloadException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
