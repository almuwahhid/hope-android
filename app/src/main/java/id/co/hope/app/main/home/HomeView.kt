package id.co.hope.app.main.home

import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.app.artikel.model.ArtikelModel
import lib.almuwahhid.base.BaseView

interface HomeView {
    interface Presenter{
        fun requestArtikel()
        fun checkSurvey()
        fun createSurvey()
    }
    interface View: BaseView {
        fun onRequestArtikel(list: MutableList<ArtikelModel>)
        fun onEmptyData(message: String)
        fun onGotoChecklist(surveyModel: SurveyModel, taskIntervensiModels: MutableList<TaskPertanyaanModel>);
        fun onGotoSurvey(message_survey: String, message_button : String)
        fun onCreateSurvey(surveyModel: SurveyModel)
        fun onFailed(message: String)
    }
}