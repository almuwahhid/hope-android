package id.ac.uny.riset.ride.menu.login

import id.co.hope.app.login.model.LoginUiModel
import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BaseView


interface LoginView {
    interface Presenter{
        abstract fun requestLogin(loginUiModel: LoginUiModel)
    }

    interface View: BaseView {
        abstract fun onSuccessLogin(model: UserModel)
        abstract fun onFailedLogin(message: String)
    }
}