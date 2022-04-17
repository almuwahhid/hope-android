package id.co.hope.app.ubahpassword

import android.content.Context
import id.co.hope.data.EndPoints
import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BasePresenter
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class UbahPasswordPresenter(context: Context?, view: UbahPasswordView.View) : BasePresenter(context), UbahPasswordView.Presenter {

    internal var view: UbahPasswordView.View? = null
    init {
        this.view = view
    }

    override fun requestUbahPassword(userModel: UserModel, newpassword: String) {
        UiLibRequest.POST(EndPoints.stringUbahPasswod(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("status").equals("200")){
                        view!!.onRequestUbahPassword()
                    } else {
                        view!!.onFailedRequestUbahPassword(response.getString("message"));
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
                param.put("data", gson.toJson(userModel))
                param.put("password", newpassword)
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}