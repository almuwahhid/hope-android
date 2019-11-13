package id.co.hope.app.biodata

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import id.co.hope.BuildConfig
import id.co.hope.R
import id.co.hope.data.StaticData
import id.co.hope.data.model.UserModel
import id.co.hope.dialogs.DialogImagePicker.DialogImagePicker
import id.co.hope.dialogs.DialogPicker.DialogPicker
import id.co.hope.module.Activity.HopePermissionActivity
import id.co.hope.utils.HopeFunction
import id.co.hope.utils.avatarview.AvatarPlaceholder
import id.co.hope.utils.avatarview.loader.PicassoLoader
import kotlinx.android.synthetic.main.activity_biodata.*
import kotlinx.android.synthetic.main.activity_biodata.edt_email
import kotlinx.android.synthetic.main.activity_biodata.edt_jk
import kotlinx.android.synthetic.main.activity_biodata.edt_nama
import kotlinx.android.synthetic.main.activity_biodata.edt_tgllahir
import kotlinx.android.synthetic.main.main_toolbar.*
import lib.almuwahhid.Activity.Interfaces.PermissionResultInterface
import lib.almuwahhid.utils.LibUi
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*

class BiodataActivity : HopePermissionActivity(), DatePickerDialog.OnDateSetListener, BiodataView.View {
    override fun onRequestUser(userModel: String) {
        LibUi.ToastShort(context, "Berhasil update biodata")
        HopeFunction.setUserPreference(context, userModel)
        initData(HopeFunction.getUserPreference(context))
    }

    var userModel: UserModel? = null
    var photo_base64 = ""

    lateinit var presenter : BiodataPresenter

    private val RequiredPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it!!.setDisplayHomeAsUpEnabled(true)
            it!!.setTitle("Biodata")
        }

        presenter = BiodataPresenter(context, this)
        userModel = HopeFunction.getUserPreference(context)
        setFormsToValidate()
        initData(userModel!!)
        initActions()
    }

    private fun initData(userModel: UserModel){
        val imageLoader = PicassoLoader()
        val refreshableAvatarPlaceholder =
            AvatarPlaceholder(userModel!!.nama)
        imageLoader.loadImage(avatar, refreshableAvatarPlaceholder, BuildConfig.base_url+"profile/"+ userModel!!.getPhoto_profil())

        edt_email.setText(userModel!!.email)
        edt_nama.setText(userModel!!.nama)
        edt_telp.setText(userModel!!.telp)
        edt_jk.setText(userModel!!.jenis_kelamin)
        edt_tgllahir.setText(HopeFunction.parseDateStringToDate(userModel!!.tgl_lahir))
        edt_programstudi.setText(userModel!!.program_studi)
        edt_semester.setText(userModel!!.semester)
        edt_pekerjaanimpian.setText(userModel!!.pekerjaan_impian)
    }

    private fun initActions(){
        lay_avatar.setOnClickListener({
            askCompactPermissions(RequiredPermissions, object : PermissionResultInterface {
                override fun permissionGranted() {
                    DialogImagePicker(context, object : DialogImagePicker.OnDialogImagePicker {
                        override fun onCameraClick() {
                            EasyImage.openCamera(this@BiodataActivity, 0)
                        }

                        override fun onFileManagerClick() {
                            EasyImage.openGallery(this@BiodataActivity, 0)
                        }
                    })
                }

                override fun permissionDenied() {
                    LibUi.ToastShort(context, "Anda perlu memberikan akses terlebih dahulu")
                }
            })
        })

        edt_pekerjaanimpian.setOnClickListener({
            DialogPicker(context, StaticData.dataPekerjaan(), edt_pekerjaanimpian.text.toString(), DialogPicker.OnDialogPicker {
                edt_pekerjaanimpian.setText(it.name)
                edt_pekerjaanimpian.setError(null)
            })
        })

        edt_jk.setOnClickListener({
            DialogPicker(context, StaticData.dataJenisKelamin(), edt_jk.text.toString(), DialogPicker.OnDialogPicker {
                edt_jk.setText(it.name)
                edt_jk.setError(null)
            })
        })

        edt_tgllahir.setOnClickListener({
            val now = Calendar.getInstance()
//                now.add(Calendar.YEAR,-7);
            val dpd = DatePickerDialog.newInstance(
                this@BiodataActivity,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.maxDate = now
            dpd.firstDayOfWeek = Calendar.MONDAY
            dpd.accentColor = ContextCompat.getColor(context, R.color.primary)
            dpd.show(fragmentManager, "Tanggal Kejadian")
        })

        btn_simpan.setOnClickListener({
            validate()
        })
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        userModel!!.setTgl_lahir(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
        edt_tgllahir.setError(null)
        edt_tgllahir.setText(dayOfMonth.toString() + " " + LibUi.monthName(monthOfYear) + " " + year)
    }

    override fun onHideLoading() {
        LibUi.hideLoadingDialog(this)
    }

    override fun onLoading() {
        LibUi.showLoadingDialog(this, R.drawable.ic_hope)
    }

    override fun onErrorConnection() {

    }

    internal var forms: ArrayList<Int> = ArrayList()
    private fun setFormsToValidate() {
        forms.add(R.id.edt_nama)
        forms.add(R.id.edt_jk)
        forms.add(R.id.edt_tgllahir)
        forms.add(R.id.edt_telp)
        forms.add(R.id.edt_programstudi)
        forms.add(R.id.edt_semester)
        forms.add(R.id.edt_pekerjaanimpian)
    }

    private fun validate(){
        if (LibUi.isFormValid(this, window.decorView, forms)) {
            userModel!!.setUserModel(edt_nama.text.toString(),
                                    edt_jk.text.toString(),
                                    edt_programstudi.text.toString(),
                                    edt_telp.text.toString(),
                                    edt_semester.text.toString(),
                                    edt_pekerjaanimpian.text.toString())
            presenter!!.updateProfile(userModel!!, photo_base64)
        }
    }

    private fun startCropActivity(uri: Uri) {
        CropImage.activity(uri)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(this@BiodataActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                avatar.setImageBitmap(HopeFunction.getBitmapFromUri(context, resultUri))
                photo_base64 = LibUi.convertToBase64(HopeFunction.getBitmapFromUri(context, resultUri));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error.toString()
                LibUi.ToastShort(context, "" + error)
            }
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this@BiodataActivity, object : DefaultCallback() {
                override fun onImagePickerError(e: Exception, source: EasyImage.ImageSource, type: Int) {
                    e.printStackTrace()

                }

                override fun onImagesPicked(imageFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
//                    Log.d(TAG, "onImagesPicked: $type")
                    startCropActivity(Uri.fromFile(imageFiles[0]))
                }

                override fun onCanceled(source: EasyImage.ImageSource, type: Int) {
                    //Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source === EasyImage.ImageSource.CAMERA) {
                        val photoFile = EasyImage.lastlyTakenButCanceledPhoto(context)
                        photoFile?.delete()
                    }
                }
            })
        }
    }
}
