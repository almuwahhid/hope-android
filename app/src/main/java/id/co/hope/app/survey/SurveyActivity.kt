package id.co.hope.app.survey

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import id.ac.uny.riset.ride.data.model.PernyataanModel
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.R
import id.co.hope.app.survey.Adapter.SurveyAdapter
import id.co.hope.app.survey.helper.SurveyHelper
import id.co.hope.app.survey.model.PertanyaanSurveyModel
import id.co.hope.app.survey.model.SurveyPayloadModel
import id.co.hope.dialogs.DialogInfo.DialogInfo
import id.co.hope.module.Activity.HopeActivity
import id.co.hope.utils.HopeFunction
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.utils.LibUi

class SurveyActivity : HopeActivity(), SurveyView.View {

    lateinit var listPertanyaanSurvey : MutableList<PertanyaanSurveyModel>
    lateinit var surveyAdapter: SurveyAdapter
    lateinit var presenter: SurveyPresenter
    lateinit var surveyModel: SurveyModel

    var list_pernyataan: MutableList<PernyataanModel>? = null
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        listPertanyaanSurvey = ArrayList()
        list_pernyataan= ArrayList()

        if(intent.hasExtra("data")){
            surveyModel = (intent.getSerializableExtra("data") as SurveyModel?)!!
        }

        toggle_ok.setInOutAnimation(R.anim.pull_in_top, R.anim.push_out_bottom);
        lay_pertanyaan.setInOutAnimation(R.anim.pull_in_right, R.anim.push_out_left)

        presenter = SurveyPresenter(context, this)
        surveyAdapter = SurveyAdapter(context, listPertanyaanSurvey!!, object : SurveyAdapter.OnSurveyAdapter{
            override fun onSurveyAdapter(position: Int) {
                makeDefaultOptions()
                listPertanyaanSurvey.get(position).isClicked = true
                surveyAdapter.notifyDataSetChanged()
                toggle_ok.show()
            }
        })
        rv_options.layoutManager = LinearLayoutManager(SurveyActivity@this)
        rv_options.adapter = surveyAdapter

        presenter!!.requestPernyataan()

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Berikan Responmu!")
        tv_date_survey.setText(HopeFunction.parseTimestampToDate(surveyModel!!.tanggal_survey))

        fab_ok.setOnClickListener({
            validate()
        })
    }

    override fun onSubmitPertanyaan(surveyPayloadModel: SurveyPayloadModel) {
        index++
        toggle_ok.hide()

        if(surveyPayloadModel.aspek)
            initDialogByAspek(surveyPayloadModel)

        if(index < list_pernyataan!!.size)
            showPertanyaan(list_pernyataan!!.get(index), index)
    }

    private fun validate(){
        presenter.submitPertanyaan(SurveyHelper.getClickedPertanyaan(listPertanyaanSurvey), list_pernyataan!!.get(index))
    }

    private fun initDialogByAspek(surveyPayloadModel: SurveyPayloadModel){
        DialogInfo(context, surveyPayloadModel.data_aspek, "Baik", R.drawable.ic_check_circle_primary_24dp, object : DialogInfo.OnDialogConfirm{
            override fun onDialogConfirmClick() {
                if(surveyPayloadModel.survey)
                    initDialogBySurveyDone(surveyPayloadModel)
            }

            override fun onCancelConfirmClick() {

            }

        })
    }

    private fun initDialogBySurveyDone(surveyPayloadModel: SurveyPayloadModel){
        DialogInfo(context, surveyPayloadModel.data_survey, "Terima Kasih Banyak", R.drawable.ic_hope, object : DialogInfo.OnDialogConfirm{
            override fun onDialogConfirmClick() {
                initDialogAnjuranAfterSurveyDone(surveyPayloadModel)
            }

            override fun onCancelConfirmClick() {
                initDialogAnjuranAfterSurveyDone(surveyPayloadModel)
            }
        })
    }

    private fun initDialogAnjuranAfterSurveyDone(surveyPayloadModel: SurveyPayloadModel){
        DialogInfo(context, "Mantap, survey selesai", "Siap", R.drawable.ic_rating, object : DialogInfo.OnDialogConfirm{
            override fun onDialogConfirmClick() {
//                initDialogAnjuranAfterSurveyDone(surveyPayloadModel)
                finish()
            }

            override fun onCancelConfirmClick() {
//                initDialogAnjuranAfterSurveyDone(surveyPayloadModel)
                finish()
            }
        })
    }

    override fun onRequestPernyataan(modelList: List<PernyataanModel>) {
        list_pernyataan!!.addAll(modelList!!)
        index = 0
        showPertanyaan(list_pernyataan!!.get(index), index)
    }

    override fun onFailedSubmitPertanyaan(message: String) {
        LibUi.ToastShort(context, message)
    }

    override fun onErrorConnection() {
        LibUi.ToastShort(context, "Ada yang bermasalah dengan server")
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(context)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_hope)
    }



    private fun makeDefaultOptions(){
        for (i in 0..(listPertanyaanSurvey!!.size - 1)) {
            listPertanyaanSurvey.get(i).isClicked = false
        }
    }

    private fun showPertanyaan(pernyataanModel: PernyataanModel, index: Int){
        listPertanyaanSurvey.clear()
        tv_index.setText(""+(index+1))
        lay_pertanyaan.hideForAWhile(this)
        tv_pertanyaan.setText(pernyataanModel.nama_pernyataan)
        listPertanyaanSurvey.addAll(SurveyHelper.getListPertanyaan(surveyModel.id_survey, pernyataanModel)!!)
        surveyAdapter.notifyDataSetChanged()
    }
}
