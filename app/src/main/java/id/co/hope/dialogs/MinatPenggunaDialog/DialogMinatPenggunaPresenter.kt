package id.co.hope.dialogs.MinatPenggunaDialog

import android.content.Context
import id.co.hope.data.EndPoints
import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BasePresenter
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class DialogMinatPenggunaPresenter(context: Context?, view: DialogMinatPenggunaView.View) : BasePresenter(context), DialogMinatPenggunaView.Presenter {
    internal var view: DialogMinatPenggunaView.View? = null
    init {
        this.view = view
    }

    override fun submitUpdate(userModel: UserModel) {
        UiLibRequest.POST(EndPoints.stringUpdateBiodata(), context, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {
                view!!.onLoading()
            }

            override fun onSuccess(response: JSONObject) {
                view!!.onHideLoading()
                try {
                    if(response.getString("result").equals("success")){
                        view!!.onSubmitUpdate(response.getJSONObject("data").toString())
                    } else {
                        view!!.onErrorConnection()
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
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}