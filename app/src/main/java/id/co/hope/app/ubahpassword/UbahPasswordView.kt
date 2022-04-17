package id.co.hope.app.ubahpassword

import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BaseView

interface UbahPasswordView {
    interface Presenter{
        fun requestUbahPassword(userModel: UserModel, newpassword : String)
    }

    interface View: BaseView {
        fun onRequestUbahPassword()
        fun onFailedRequestUbahPassword(message: String)
    }
}