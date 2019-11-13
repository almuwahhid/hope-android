package id.co.hope.app.surveysaya

import android.content.Context
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.data.EndPoints
import id.co.hope.utils.HopeFunction
import lib.almuwahhid.base.BasePresenter
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class SurveySayaPresenter(context: Context?, view: SurveySayaView.View) : BasePresenter(context), SurveySayaView.Presenter {
    lateinit var view: SurveySayaView.View

    init {
        this.view = view
    }
    override fun requestSurveySaya() {
        UiLibRequest.POST(EndPoints.stringSurveySaya(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view.onHideLoading()
                try {
                    if (response.getString("result") == "success") {
                        val surveySayaViewList = ArrayList<SurveyModel>()
                        val data = response.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            surveySayaViewList.add(
                                gson.fromJson(data.getJSONObject(i).toString(), SurveyModel::class.java)
                            )
                        }
                        view.onRequestSurveySaya(surveySayaViewList)
                    } else {

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view.onHideLoading()
                view.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["data"] = gson.toJson(HopeFunction.getUserPreference(context))
                return requestParam
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}