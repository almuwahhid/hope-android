package id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword

import android.content.Context
import id.co.hope.data.EndPoints
import lib.almuwahhid.base.BasePresenter

import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class DialogLupaPasswordPresenter(context: Context?, view: DialogLupaPasswordView.View) : BasePresenter(context), DialogLupaPasswordView.Presenter {
    internal var view: DialogLupaPasswordView.View? = null
    init {
        this.view = view
    }
    override fun requestLupaPassword(email: String) {
        UiLibRequest.POST(EndPoints.stringLupaPassword(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("status").equals("success")){
                        view!!.onRequestLupaPassword(true)
                    } else {
                        view!!.onRequestLupaPassword(false)
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
                param.put("data", email)
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}