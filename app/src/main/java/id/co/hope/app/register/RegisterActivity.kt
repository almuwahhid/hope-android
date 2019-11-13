package id.co.hope.app.register

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import id.ac.uny.riset.ride.menu.register.RegisterPresenter
import id.ac.uny.riset.ride.menu.register.RegisterView
import id.co.hope.R
import id.co.hope.app.login.LoginActivity
import id.co.hope.data.StaticData
import id.co.hope.data.model.UserModel
import id.co.hope.dialogs.DialogPicker.DialogPicker
import id.co.hope.module.Activity.HopeActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.utils.LibUi
import lib.almuwahhid.utils.LibUi.monthName
import java.util.*

class RegisterActivity : HopeActivity(), DatePickerDialog.OnDateSetListener, RegisterView.View {

    var userModel: UserModel = UserModel()
    var presenter: RegisterPresenter? = null

    var dialogJK: DialogPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Mendaftar")
        }

        presenter = RegisterPresenter(context, this)
        setFormsToValidate()

        tv_login.setOnClickListener({
            startActivity(Intent(context, LoginActivity::class.java))
        })

        btn_register.setOnClickListener({
            validate()
        })

        edt_jk.setOnClickListener({
            initDialogJk()
        })

        edt_tgllahir.setOnClickListener({
            val now = Calendar.getInstance()
//                now.add(Calendar.YEAR,-7);
            val dpd = DatePickerDialog.newInstance(
                this@RegisterActivity,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.maxDate = now
            dpd.firstDayOfWeek = Calendar.MONDAY
            dpd.accentColor = ContextCompat.getColor(context, R.color.primary)
            dpd.show(fragmentManager, "Tanggal Kejadian")
        })

//        initDialogJk()
    }

    internal var forms: ArrayList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_email)
        forms.add(R.id.edt_nama)
        forms.add(R.id.edt_password)
        forms.add(R.id.edt_tgllahir)
        forms.add(R.id.edt_telp)
    }

    override fun onSubmitRegister(isSuccess: Boolean, message: String) {
        LibUi.ToastShort(context, message)
        if(isSuccess)
            finish()
    }

    override fun onErrorConnection() {
        LibUi.ToastShort(context, "Bermasalah dengan server")
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(context)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(context, R.drawable.ic_hope)
    }

    private fun validate() {
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            userModel.setUserModel(
                edt_email.text.toString(),
                edt_password.text.toString(),
                edt_nama.text.toString(),
                edt_jk.text.toString(),
                edt_telp.text.toString())
            presenter!!.submitRegister(userModel)
        }
    }

    private fun initDialogJk(){
        dialogJK = DialogPicker(context, StaticData.dataJenisKelamin(), edt_jk.text.toString(), DialogPicker.OnDialogPicker {
            edt_jk.setText(it.name)
            edt_jk.setError(null)
        })
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        userModel.setTgl_lahir(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
        edt_tgllahir.setError(null)
        edt_tgllahir.setText(dayOfMonth.toString() + " " + monthName(monthOfYear) + " " + year)
    }
}
