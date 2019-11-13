package id.ac.uny.riset.ride.Service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.gson.Gson
import id.co.hope.data.EndPoints
import id.co.hope.utils.HopeFunction
import id.co.hope.utils.NotificationUtil
import lib.almuwahhid.utils.UiLibRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CheckIntervensiJovScheduler : JobService() {
    override fun onStopJob(p0: JobParameters?): Boolean {
        jobFinished(p0, false)
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        checkIntervensiHarian(p0!!)
        return true;
    }

    fun checkIntervensiHarian(jobParameters: JobParameters){
        val gson = Gson()
        UiLibRequest.POST(EndPoints.stringCheckIntervensiToday(), applicationContext, object : UiLibRequest.OnPostRequest {
            override fun onPreExecuted() {

            }

            override fun onSuccess(response: JSONObject) {
                jobFinished(jobParameters, false);
                try {
                    if(response.getString("result").equals("success")){
                        NotificationUtil(applicationContext).showPersonChatNotif()
                    } else {

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(error: String) {
                jobFinished(jobParameters, true);
            }

            override fun requestParam(): Map<String, String> {
                val param = HashMap<String, String>()
                param.put("data", gson.toJson(HopeFunction.getUserPreference(applicationContext)))
                return param
            }

            override fun requestHeaders(): Map<String, String> {
                return HashMap()
            }
        })
    }
}