package id.co.hope.app.survey

import android.content.Context
import id.ac.uny.riset.ride.data.model.AspekIndikatorModel
import id.ac.uny.riset.ride.data.model.PernyataanModel
import id.co.hope.app.survey.helper.SurveyHelper
import id.co.hope.app.survey.model.PertanyaanSurveyModel
import id.co.hope.app.survey.model.SurveyPayloadModel
import id.co.hope.data.EndPoints
import lib.almuwahhid.base.BasePresenter
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class SurveyPresenter (context: Context?, view: SurveyView.View) : BasePresenter(context), SurveyView.Presenter {

    lateinit var view: SurveyView.View

    init {
        this.view = view
    }

    override fun submitPertanyaan(pertanyaanSurveyModel: PertanyaanSurveyModel, pernyataanModel: PernyataanModel) {
        UiLibRequest.POST(EndPoints.stringSubmitPertanyaan(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoading()
                try {
                    val surveyPayloadModel = gson!!.fromJson(response.toString(), SurveyPayloadModel::class.java)
                    if(surveyPayloadModel.result.equals("success")){
                        view.onSubmitPertanyaan(surveyPayloadModel)
                    } else {
                        view.onFailedSubmitPertanyaan(surveyPayloadModel.result)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    view.onFailedSubmitPertanyaan("Ada yang bermasalah dengan service")
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoading()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["data"] = gson.toJson(pertanyaanSurveyModel)
                requestParam["pernyataan"] = gson.toJson(pernyataanModel)
                return requestParam
            }

            override fun requestHeaders(): Map<String, String>? {
                val requestHeader = HashMap<String, String>()
                return requestHeader
            }
        })
    }

    override fun requestPernyataan() {
        UiLibRequest.POST(EndPoints.stringPernyataan(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoading()
                try {
                    var list_aspekIndikator : MutableList<AspekIndikatorModel> = ArrayList()
                    var data = response.getJSONArray("data")

                    for (i in 0..(data.length() - 1)) {
                        list_aspekIndikator!!.add(gson.fromJson(data.get(i).toString(), AspekIndikatorModel::class.java))
                    }

                    view.onRequestPernyataan(SurveyHelper.convertFromIndikatorToPernyataan(list_aspekIndikator))

                } catch (e: JSONException) {
                    e.printStackTrace()
                    view.onFailedSubmitPertanyaan("Ada yang bermasalah dengan service")
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoading()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                return requestParam

            }

            override fun requestHeaders(): Map<String, String>? {
                val requestParam = HashMap<String, String>()
                return requestParam
            }
        })
    }
}