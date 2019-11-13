package id.ac.uny.riset.ride.menu.register

import android.content.Context
import id.co.hope.data.EndPoints
import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BasePresenter

import lib.almuwahhid.utils.LibConstant
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class RegisterPresenter(context: Context?, view: RegisterView.View): BasePresenter(context), RegisterView.Presenter {
    internal var view: RegisterView.View? = null

    init {
        this.view = view
    }

    override fun  submitRegister(userModel: UserModel) {
        UiLibRequest.POST(EndPoints.stringRegister(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if (response.getInt("status") == LibConstant.CODE_SUCCESS) {
                        view!!.onSubmitRegister(true, response.getString("message"))
                    } else {
                        view!!.onSubmitRegister(false, response.getString("message"))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    view!!.onSubmitRegister(false, response.getString("message"))
                }
            }

            override fun onFailure(error: String) {
                view!!.onHideLoading()
                view!!.onSubmitRegister(false, "Bermasalah dengan Server")
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param["data"] = gson.toJson(userModel)
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }


}