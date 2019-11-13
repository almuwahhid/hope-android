package id.co.hope.app.biodata

import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BaseView

interface BiodataView {
    interface Presenter{
        fun updateProfile(userModel: UserModel, photo: String)
    }

    interface View : BaseView{
        fun onRequestUser(userModel: String)
    }
}