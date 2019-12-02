package id.co.hope.app.main.profile

import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import id.co.hope.R
import kotlinx.android.synthetic.main.fragment_profile.*
import lib.almuwahhid.Activity.FragmentPermission
import lib.almuwahhid.utils.LibUi
import id.co.hope.data.Preferences
import id.co.hope.utils.HopeFunction
import id.co.hope.app.login.LoginActivity
import id.co.hope.data.model.UserModel
import lib.almuwahhid.utils.AlertDialogBuilder
import id.co.hope.utils.avatarview.loader.PicassoLoader
import id.co.hope.utils.avatarview.AvatarPlaceholder
import org.joda.time.PeriodType
import org.joda.time.LocalDate
import org.joda.time.Period
import java.lang.Exception
import id.co.hope.app.hubungikonseler.HubungiConselerDialog
import id.co.hope.app.tentangaplikasi.TentangAplikasiActivity
import id.co.hope.app.biodata.BiodataActivity
import id.co.hope.BuildConfig
import id.co.hope.app.ubahpassword.UbahPasswordDialog
import id.co.hope.app.surveysaya.SurveySayaActivity
import id.co.hope.app.fotopreview.FotoPreviewActivity
import id.co.hope.app.main.MainViewModel


class ProfileFragment : FragmentPermission() {

//    val gson: Gson = Gson()
lateinit var viewModel: MainViewModel

    companion object{
        fun newInstance(mainViewModel: MainViewModel): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.viewModel = mainViewModel
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_logout.setOnClickListener({
            AlertDialogBuilder(context,
                                "Apakah Anda yakin ingin logout?",
                                      "Ya",
                                        "Tidak", object : AlertDialogBuilder.OnAlertDialog{
                    override fun onPositiveButton(dialog: DialogInterface?) {
                        viewModel.doLogout(true)
                        LibUi.setSPString(context, Preferences.USER_INTRO, "")
                        HopeFunction.logoutUser(context)
                        startActivity(Intent(context, LoginActivity::class.java))
                        activity!!.finish()
                    }

                    override fun onNegativeButton(dialog: DialogInterface?) {

                    }

                })
        })

        lay_hubungikonseler.setOnClickListener({
            HubungiConselerDialog(context)
        })
        lay_klasifikasisaya.setOnClickListener({
            startActivity(Intent(context, SurveySayaActivity::class.java))
        })

        lay_tentangaplikasi.setOnClickListener({
            startActivity(Intent(context, TentangAplikasiActivity::class.java))
        })

        btn_edit.setOnClickListener({
            startActivity(Intent(context, BiodataActivity::class.java))
        })
        lay_ubahpassword.setOnClickListener({
            UbahPasswordDialog(context)
        })

    }

    override fun onResume() {
        super.onResume()
        initData(HopeFunction.getUserPreference(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    private fun initData(userModel: UserModel){
        val imageLoader = PicassoLoader()
        val refreshableAvatarPlaceholder =
            AvatarPlaceholder(userModel.nama)
        imageLoader.loadImage(avatar, refreshableAvatarPlaceholder, BuildConfig.base_url+"profile/"+ userModel.getPhoto_profil())

        tv_nama.setText(userModel.nama)
        tv_umur.setText(convertAge(userModel.tgl_lahir))
        tv_pekerjaan.setText(userModel.getPekerjaan_impian())

        avatar.setOnClickListener({
            val data = Bundle()
            data.putString("image", BuildConfig.base_url+"profile/"+ userModel.getPhoto_profil())

            if (!HopeFunction.isPreLolipop()) {
                //                    Pair<View, String> pair1 = Pair.create(view, view.getTransitionName());
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    avatar,
                    ViewCompat.getTransitionName(avatar)
                )
                startActivity(Intent(context, FotoPreviewActivity::class.java).putExtras(data), options.toBundle())
            } else {
                startActivity(Intent(context, FotoPreviewActivity::class.java).putExtras(data))
            }
        })
    }

    private fun convertAge(bdate : String): String{
        try {
            val birthdate = LocalDate(Integer.valueOf(bdate.split("-")[0]), Integer.valueOf(bdate.split("-")[1]), Integer.valueOf(bdate.split("-")[2]))      //Birth date
            val now = LocalDate()                        //Today's date
            val period = Period(birthdate, now, PeriodType.yearMonthDay())
            return ""+period.years+" tahun";
        } catch (e: Exception){
            return bdate;
        }

    }
}
