package id.co.hope.app.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import id.ac.uny.riset.ride.menu.login.LoginPresenter
import id.ac.uny.riset.ride.menu.login.LoginView
import id.co.hope.R
import id.co.hope.app.login.model.LoginUiModel
import id.co.hope.app.lupapassword.LupaPasswordDialog
import id.co.hope.app.main.MainActivity
import id.co.hope.app.register.RegisterActivity
import id.co.hope.data.model.UserModel
import id.co.hope.module.Activity.HopeActivity
import id.co.hope.utils.HopeFunction
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_helper.*
import lib.almuwahhid.utils.LibUi
import java.util.ArrayList

class LoginActivity : HopeActivity(), LoginView.View {

    var presenter: LoginPresenter? = null;
    var gson: Gson? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(HopeFunction.checkUserPreference(this)){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        gson = Gson()
        presenter = LoginPresenter(this, this)
        setFormsToValidate()

        helper_loading_top.setInOutAnimation(R.anim.pull_in_bottom, R.anim.push_out_top)

        tv_register.setOnClickListener({
            startActivity(Intent(context, RegisterActivity::class.java))
        })

        btn_login.setOnClickListener({
            validate()
        })
        tv_lupapassword.setOnClickListener({
            LupaPasswordDialog(this)
        })
    }

    internal var forms: MutableList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_password)
        forms.add(R.id.edt_email)
    }

    private fun validate() {
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            presenter!!.requestLogin(LoginUiModel(edt_email.text.toString(), edt_password.text.toString()))
        }
    }

    override fun onSuccessLogin(model: UserModel) {
        HopeFunction.setUserPreference(this, gson!!.toJson(model))
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onFailedLogin(message: String) {
        tv_loading_top.setText(message)
        helper_loading_top.showForAWhile(this)
    }

    override fun onErrorConnection() {

    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(this)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_hope)
    }
}
