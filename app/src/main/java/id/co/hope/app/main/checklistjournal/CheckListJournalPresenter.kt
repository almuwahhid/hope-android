package id.co.hope.app.main.checklistjournal

import android.content.Context
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.app.main.home.HomePresenter
import id.co.hope.data.EndPoints
import id.co.hope.utils.HopeFunction
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class CheckListJournalPresenter(context: Context?, checklistView: CheckListJournalView.View)  : HomePresenter(context, checklistView), CheckListJournalView.Presenter{
    internal var checklistView: CheckListJournalView.View? = null
    init {
        this.checklistView = checklistView
    }

    override fun confirmTask(taskIntervensiModel: TaskPertanyaanModel) {
        UiLibRequest.POST(EndPoints.stringUpdateTaskPertanyaan(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                checklistView!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                checklistView!!.onHideLoading()
                try {
                    if (response.getString("result") == "success") {
                        if (response.getBoolean("done")) {
                            checklistView!!.onSubmitTaskPertanyaan(true, "Berhasil submit", true)
                        } else {
                            checklistView!!.onSubmitTaskPertanyaan(true, "Berhasil submit", false)
                        }
                    } else {
                        checklistView!!.onSubmitTaskPertanyaan(false, "Ada yang bermasalah dengan service", false)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    checklistView!!.onSubmitTaskPertanyaan(false, "Ada yang bermasalah dengan service", false)
                }

            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onErrorConnection()
            }

            override fun requestParam(): Map<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["data"] = gson.toJson(taskIntervensiModel)
                requestParam["user"] = gson.toJson(HopeFunction.getUserPreference(context))
                return requestParam
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}