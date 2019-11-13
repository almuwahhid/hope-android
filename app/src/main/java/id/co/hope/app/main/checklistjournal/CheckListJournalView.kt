package id.co.hope.app.main.checklistjournal

import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.app.artikel.model.ArtikelModel
import id.co.hope.app.main.home.HomeView
import lib.almuwahhid.base.BaseView

interface CheckListJournalView {
    interface Presenter{
        fun confirmTask(taskIntervensiModel: TaskPertanyaanModel)
    }
    interface View: HomeView.View {
        fun onSubmitTaskPertanyaan(isSuccess: Boolean, message: String, isDone: Boolean)
    }
}