package id.co.hope.app.main.home

import android.content.Context
import android.util.Log
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.app.artikel.model.ArtikelModel
import id.co.hope.data.EndPoints
import id.co.hope.utils.HopeFunction
import lib.almuwahhid.base.BasePresenter
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

open class HomePresenter (context: Context?, view: HomeView.View) : BasePresenter(context), HomeView.Presenter {
    override fun checkSurvey() {
        UiLibRequest.POST(EndPoints.stringCheckSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    when(response.getString("result")){
                        "survey" -> {
                            var intervensis: MutableList<TaskPertanyaanModel> = ArrayList()
                            val array_intervensi: JSONArray = response.getJSONArray("intervensi")

                            for (i in 0..(array_intervensi.length() - 1)) {
                                intervensis!!.add(gson.fromJson(array_intervensi.get(i).toString(), TaskPertanyaanModel::class.java))
                            }

                            Log.d("contoh ", ""+intervensis.size)
                            view!!.onGotoChecklist(
                                gson.fromJson(response.getString("data"), SurveyModel::class.java),
                                intervensis
                            )
                        }
                        "new" -> {
                            view!!.onGotoSurvey("Sepertinya Anda anak baru ya! gass kan sajja", "Coba sekarang")
                        }
                        "second" -> {
                            view!!.onGotoSurvey("Kamu udah pernah ngikutin lebih dari sekali, gimana rasanya?", "Coba lagi")
                        }
                        "first" -> {
                            view!!.onGotoSurvey("Eh kamu udah coba sekali, selamat ya", "Coba lagi ah")
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailed("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(HopeFunction.getUserPreference(context)))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }

    override fun createSurvey() {
        UiLibRequest.POST(EndPoints.stringMakeSurvey(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("result").equals("success")){
                        view!!.onCreateSurvey(gson.fromJson(response.getJSONObject("data").toString(), SurveyModel::class.java))
                    } else {

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onFailed("Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(HopeFunction.getUserPreference(context)))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }

    internal var view: HomeView.View? = null
    init {
        this.view = view
    }
    override fun requestArtikel() {
        UiLibRequest.POST(EndPoints.stringArtikel(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("status").equals("200")){
                        var datas : MutableList<ArtikelModel> = ArrayList()
                        var data = response.getJSONArray("data")
                        for (i in 0..(data.length() - 1)) {
                            datas!!.add(gson.fromJson(data.get(i).toString(), ArtikelModel::class.java))
                        }
                        view!!.onRequestArtikel(datas)
                    } else {
                        view!!.onEmptyData(response.getString("message"));
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()

                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}