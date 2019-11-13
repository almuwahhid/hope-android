package id.co.hope.app.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    internal var openChecklistJournal: MutableLiveData<Boolean>? = null
    internal var introStartSurvey: MutableLiveData<Boolean>? = null

    fun initOpenCheckListJournal(): LiveData<Boolean> {
        if (openChecklistJournal == null) {
            openChecklistJournal = MutableLiveData<Boolean>()
            //            requestPinned.postValue(false);
            openChecklistJournal!!.postValue(false)
        }
        return openChecklistJournal!!
    }

    fun updateChecklistJournal(isShow: Boolean) {
        if (openChecklistJournal != null) {
            openChecklistJournal!!.postValue(isShow)
        }
    }

    fun initStartSurvey(): LiveData<Boolean> {
        if (introStartSurvey == null) {
            introStartSurvey = MutableLiveData<Boolean>()
            //            requestPinned.postValue(false);
            introStartSurvey!!.postValue(false)
        }
        return introStartSurvey!!
    }

    fun updateStartSurvey(isShow: Boolean) {
        if (introStartSurvey != null) {
            introStartSurvey!!.postValue(isShow)
        }
    }
}