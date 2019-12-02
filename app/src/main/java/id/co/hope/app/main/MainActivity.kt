package id.co.hope.app.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.MenuItem
import id.co.hope.R
import id.co.hope.Service.AlarmReceiver
import id.co.hope.app.main.checklistjournal.ChecklistJournalFragment
import id.co.hope.app.main.home.HomeFragment
import id.co.hope.app.main.profile.ProfileFragment
import id.co.hope.data.Preferences
import id.co.hope.data.StringConstant
import id.co.hope.data.model.UserModel
import id.co.hope.dialogs.MinatPenggunaDialog.DialogMinatPengguna
import id.co.hope.module.Activity.HopeActivity
import id.co.hope.utils.HopeFunction
import kotlinx.android.synthetic.main.activity_main.*
import lib.almuwahhid.utils.LibUi

class MainActivity : HopeActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    internal var fragment: Fragment? = null
    internal var active_fragment = 0
    internal var after_active_fragment = 0

    lateinit var homeFragment : HomeFragment
    lateinit var checklistFragment : ChecklistJournalFragment
    lateinit var profileFragment : ProfileFragment
    internal var mFragmentManager = supportFragmentManager
    lateinit var viewModel : MainViewModel

    private var alarmReceiver: AlarmReceiver? = null

    lateinit var userModel : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userModel = HopeFunction.getUserPreference(context)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        with(viewModel){
            initOpenCheckListJournal().observe(this@MainActivity, Observer {
                if(it!!){
                    initActiveFragment(1)
                    fragment = checklistFragment
                    initializeNavFragment(fragment!!)
                    nav.getMenu().findItem(R.id.nav_checklist).setChecked(true);
                }
            })

            initLogout().observe(this@MainActivity, Observer {
                alarmReceiver!!.cancelAlarm(this@MainActivity, AlarmReceiver.TYPE_REPEATING);
            })
        }

        homeFragment = HomeFragment.newInstance(viewModel)
        checklistFragment = ChecklistJournalFragment.newInstance()
        profileFragment = ProfileFragment.newInstance(viewModel)

        if (savedInstanceState != null) {
            fragment = supportFragmentManager.getFragment(savedInstanceState, "fragment")
        } else {
            fragment = homeFragment
        }
        initializeNavFragment(fragment!!)
        nav.setOnNavigationItemSelectedListener(this)

        if(!LibUi.getSPBoolean(context, Preferences.HOME_INTRO)){
            introChecklistJournal()
        }

        alarmReceiver = AlarmReceiver()
        if(!alarmReceiver!!.isAlarmSet(context, AlarmReceiver.TYPE_REPEATING)){
            alarmReceiver!!.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,"");
        }
    }

    private fun introChecklistJournal(){
        HopeFunction.showIntroCase(this,
            findViewById(R.id.nav_checklist),
            "Checklist Journal",
            getString(R.string.intro_dummy),
            true,
            object : LibUi.OnEventChange {
                override fun onChange() {
                    LibUi.setSPBoolean(this@MainActivity, Preferences.HOME_INTRO, true)
                    introProfile()
                }
            })
    }

    private fun introProfile(){
        HopeFunction.showIntroCase(this,
            findViewById(R.id.nav_profile),
            "Profile",
            getString(R.string.intro_dummy),
            true,
            object : LibUi.OnEventChange {
                override fun onChange() {
                    viewModel.updateStartSurvey(true)
                }
            })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("hut", ":huy")
        when (item.itemId) {
            R.id.nav_home -> {
                initActiveFragment(0)
                fragment = homeFragment
            }
            R.id.nav_checklist -> {
                initActiveFragment(1)
                fragment = checklistFragment
            }
            R.id.nav_profile -> {
                initActiveFragment(2)
                fragment = profileFragment
            }
        }
        initializeNavFragment(fragment!!)
        return true
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Save the fragment's instance
        if (fragment!!.isAdded()) {
            supportFragmentManager.putFragment(outState, "fragment", fragment!!)
        }
    }

    private fun initActiveFragment(a: Int) {
        active_fragment = a
    }

    private fun initializeNavFragment(curFragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()

        if (mFragmentManager.findFragmentByTag(curFragment.javaClass.simpleName) == null) {
            transaction.add(R.id.contentContainer, curFragment, curFragment.javaClass.simpleName)
        }

        val tagMain = mFragmentManager.findFragmentByTag(homeFragment.javaClass.getSimpleName())
        val tagChecklist = mFragmentManager.findFragmentByTag(checklistFragment.javaClass.getSimpleName())
        val tagProfil = mFragmentManager.findFragmentByTag(profileFragment.javaClass.getSimpleName())
        hideFragment(transaction, tagMain, tagChecklist, tagProfil)
        showFragment(curFragment, transaction, tagMain, tagChecklist, tagProfil)

        after_active_fragment = active_fragment
        transaction.commitAllowingStateLoss()
    }

    private fun showFragment(
        curFragment: Fragment,
        transaction: FragmentTransaction,
        tagMain: Fragment?,
        tagChecklist: Fragment?,
        tagProfile: Fragment?
    ) {
        if (curFragment.javaClass.simpleName == homeFragment.javaClass.getSimpleName()) {
            if (tagMain != null) {
                transaction.show(tagMain)
            }
        }

        if (curFragment.javaClass.simpleName == checklistFragment.javaClass.getSimpleName()) {
            if (tagChecklist != null) {
                transaction.show(tagChecklist)
            }
        }

        if (curFragment.javaClass.simpleName == profileFragment.javaClass.getSimpleName()) {
            if (tagProfile != null) {
                transaction.show(tagProfile)
            }
        }
    }

    private fun hideFragment(transaction: FragmentTransaction,
                             tagMain: Fragment?,
                             tagChecklist: Fragment?,
                             tagProfile: Fragment?) {
        if (tagMain != null) {
            transaction.hide(tagMain)
        }
        if (tagChecklist != null) {
            transaction.hide(tagChecklist)
        }
        if (tagProfile != null) {
            transaction.hide(tagProfile)
        }
    }

    override fun onResume() {
        super.onResume()
        if(userModel.pekerjaan_impian.equals("") || userModel.universitas.equals("") || userModel.semester.equals("")){
            DialogMinatPengguna(context, userModel);
        }
    }
}
