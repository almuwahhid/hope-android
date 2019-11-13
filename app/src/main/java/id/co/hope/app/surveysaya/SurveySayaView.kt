package id.co.hope.app.surveysaya

import id.ac.uny.riset.ride.data.model.SurveyModel
import lib.almuwahhid.base.BaseView

interface SurveySayaView {
    interface Presenter {
        fun requestSurveySaya()
    }

    interface View : BaseView {
        fun onRequestSurveySaya(surveySayaModelList: List<SurveyModel>)
        fun onEmptyData()
    }
}