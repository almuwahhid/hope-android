package id.co.hope.dialogs.DialogDetailAchievement.adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.R

class DialogDetailAchievementAdapter (context: Context, surveyModel: SurveyModel) : RecyclerView.Adapter<DialogDetailAchievementAdapter.Holder>() {

    lateinit var surveyModel: SurveyModel
    lateinit var context: Context

    init {
        this.context = context
        this.surveyModel = surveyModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_dialog_detail_achievement, parent, false)
        return DialogDetailAchievementAdapter.Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return surveyModel.komentar_aspek.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_description!!.setText(surveyModel.komentar_aspek.get(position))
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_description: TextView? = null
        init {
            this.setIsRecyclable(false)
            tv_description = itemView.findViewById(R.id.tv_description)
        }
    }
}
