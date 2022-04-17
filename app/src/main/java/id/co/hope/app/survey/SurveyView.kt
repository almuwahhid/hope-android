package id.co.hope.app.survey

import id.ac.uny.riset.ride.data.model.PernyataanModel
import id.co.hope.app.survey.model.PertanyaanSurveyModel
import id.co.hope.app.survey.model.SurveyPayloadModel
import lib.almuwahhid.base.BaseView

interface SurveyView {
    interface Presenter {
        fun submitPertanyaan(pertanyaanSurveyModel: PertanyaanSurveyModel, pernyataanModel: PernyataanModel)
        fun requestPernyataan()

    }

    interface View : BaseView {
        fun onSubmitPertanyaan(surveyPayloadModel: SurveyPayloadModel)
        fun onRequestPernyataan(modelList: List<PernyataanModel>)
        fun onFailedSubmitPertanyaan(message: String)
    }
}