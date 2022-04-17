package id.ac.uny.riset.ride.menu.register

import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BaseView


interface RegisterView {
    interface Presenter{
        fun submitRegister(userModel: UserModel)
    }

    interface View: BaseView {
        fun onSubmitRegister(isSuccess: Boolean, message: String)
    }
}