package id.co.hope.app.main.checklistjournal

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.R
import id.co.hope.app.artikel.model.ArtikelModel
import kotlinx.android.synthetic.main.layout_checklistjournal_new.*
import lib.almuwahhid.Activity.FragmentPermission
import id.co.hope.app.main.checklistjournal.CheckListJournalPresenter
import id.co.hope.app.survey.SurveyActivity
import id.co.hope.app.main.checklistjournal.Adapter.ChecklistJournalAdapter
import kotlinx.android.synthetic.main.layout_helper.*
import id.co.hope.dialogs.DialogInfo.DialogInfo
import kotlinx.android.synthetic.main.fragment_checklist_journal.*
import lib.almuwahhid.utils.LibUi
import id.co.hope.utils.HopeFunction
import id.co.hope.dialogs.DialogSubmitTaskPertanyaan.DialogSubmitTaskPertanyaan
import id.co.hope.data.Preferences

class ChecklistJournalFragment : FragmentPermission(), CheckListJournalView.View {
    lateinit var checklistJournalAdapter: ChecklistJournalAdapter
    lateinit var taskIntervensiModels: MutableList<TaskPertanyaanModel>
    lateinit var presenter: CheckListJournalPresenter
    var fromRefresh = false


    companion object{
        fun newInstance(): ChecklistJournalFragment {
            return ChecklistJournalFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checklistJournalAdapter = ChecklistJournalAdapter(context!!, taskIntervensiModels, object : ChecklistJournalAdapter.OnChecklistJournalAdapter{
            override fun onPertanyaanClick(intervensiModel: TaskPertanyaanModel) {
                DialogSubmitTaskPertanyaan(context, intervensiModel, object : DialogSubmitTaskPertanyaan.OnDialogSubmitPertanyaan{
                    override fun onSubmitTaskPertanyaan(taskPertanyaanModel: TaskPertanyaanModel?) {
                        presenter.confirmTask(taskPertanyaanModel!!)
                    }
                })
            }

        })
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = checklistJournalAdapter

        swipe.setOnRefreshListener {
            fromRefresh = true
            presenter.checkSurvey()
            swipe.isRefreshing = false
        }
        btn_survey.setOnClickListener({
            presenter.createSurvey()
        })
        img_close.setOnClickListener({
            lay_alert.visibility = View.GONE
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_checklist_journal, container, false)
        presenter = CheckListJournalPresenter(context, this)
        taskIntervensiModels = ArrayList()
        return view
    }

    override fun onSubmitTaskPertanyaan(isSuccess: Boolean, message: String, isDone: Boolean) {
        LibUi.ToastShort(context, message)
        if(isSuccess){
            fromRefresh = true
            presenter.checkSurvey()
        }
        if(isDone){
            DialogInfo(context, "Selamat kamu sudah menyelesaikan semua intervensi yang ada, semoga dapat membantumu dalam mencari pekerjaan ya", "Baik, akan saya lakukan", R.drawable.ic_motivation, object : DialogInfo.OnDialogConfirm{
                override fun onDialogConfirmClick() {

                }

                override fun onCancelConfirmClick() {

                }
            })
        }
    }

    override fun onRequestArtikel(list: MutableList<ArtikelModel>) {

    }

    override fun onEmptyData(message: String) {

    }

    override fun onGotoChecklist(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>) {
        lay_new.visibility = View.GONE
        tv_survey_title.setText(HopeFunction.parseTimestampToDate(surveyModel.tanggal_survey))
        this.taskIntervensiModels!!.clear()
        this.taskIntervensiModels!!.addAll(taskIntervensiModels)
        checklistJournalAdapter.notifyDataSetChanged()
        if(!fromRefresh){
            initDialogConfirm("Hai, kamu memiliki beberapa tugas yang harus dikerjakan! Yuk mulai!", "Okay, akan kumulai!", R.drawable.ic_hope, object : DialogInfo.OnDialogConfirm{
                override fun onCancelConfirmClick() {

                }

                override fun onDialogConfirmClick() {

                }
            })
        }

        if(!LibUi.getSPBoolean(context, Preferences.CHECKLIST_INTRO)){
            introChecklistJournal()
        }
    }

    private fun introChecklistJournal(){
        HopeFunction.showIntroCase(activity,
            img_calendar,
            "Survey Aktif",
            "Ini adalah tanggal survey aktif untuk task intervensi yang harus kamu kerjakan, tekan salah satu daftar intervensi untuk mengerjakan intervensi sesuai tanggal yang berlaku",
            true,
            object : LibUi.OnEventChange {
                override fun onChange() {
                    LibUi.setSPBoolean(context, Preferences.CHECKLIST_INTRO, true)
                }
            })
    }

    override fun onGotoSurvey(message_survey: String, message_button: String) {
        initNewSurvey(message_button, message_survey)
    }

    override fun onCreateSurvey(surveyModel: SurveyModel) {
        startActivity(Intent(context, SurveyActivity::class.java).putExtra("data", surveyModel))
    }

    override fun onFailed(message: String) {
        LibUi.ToastShort(context, message)
    }

    override fun onErrorConnection() {

    }

    override fun onResume() {
        super.onResume()
        presenter.checkSurvey()
    }

    override fun onHideLoading() {
        helper_loading_full.visibility = View.GONE
    }

    override fun onLoading() {
        helper_loading_full.visibility = View.VISIBLE
    }

    private fun initNewSurvey(message_button : String, message_survey : String){
        lay_new.visibility = View.VISIBLE
        btn_survey.setText(message_button)
        tv_description_new.setText(message_survey)
    }

    private fun initDialogConfirm(title: String, btn_title: String, img_source : Int, onDialogConfirm: DialogInfo.OnDialogConfirm){
        DialogInfo(context, title, btn_title, img_source, onDialogConfirm)
    }
}
