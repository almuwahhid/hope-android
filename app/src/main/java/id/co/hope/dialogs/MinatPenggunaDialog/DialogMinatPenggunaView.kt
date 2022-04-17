package id.co.hope.dialogs.MinatPenggunaDialog

import id.co.hope.data.model.UserModel
import lib.almuwahhid.base.BaseView

interface DialogMinatPenggunaView {
    interface Presenter{
        fun submitUpdate(userModel: UserModel)
    }

    interface View : BaseView{
        fun onSubmitUpdate(userModel: String)
    }
}