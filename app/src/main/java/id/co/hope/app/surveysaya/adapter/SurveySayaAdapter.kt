package id.co.hope.app.surveysaya.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.ac.uny.riset.ride.data.model.SurveyModel
import id.co.hope.R
import id.co.hope.utils.HopeFunction
import id.co.hope.utils.timelineview.TimelineView

class SurveySayaAdapter (context: Context, list: MutableList<SurveyModel>, private val onSurveySayaAdapter: OnSurveySayaAdapter) : RecyclerView.Adapter<SurveySayaAdapter.Holder>() {

    lateinit var list: MutableList<SurveyModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_survey_saya, parent, false)
        return Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val surveyModel = list.get(position)
        holder.card_survey!!.setOnClickListener({
            onSurveySayaAdapter.onSurveyClick(surveyModel)
        })
        holder.tv_date!!.setText(HopeFunction.parseTimestampToSimpleDate(surveyModel.tanggal_survey))
        holder.tv_month_year!!.setText(HopeFunction.parseTimestampToSimpleMonthYear(surveyModel.tanggal_survey))
        holder.tv_title!!.setText(surveyModel.nama_status)
        holder.tv_desc!!.setText(surveyModel.deskripsi_status)
        when(surveyModel.id_status_identitas_religius){
            "1"->holder.img_medal!!.setImageResource(R.drawable.ic_medal_gold)
            "2"->holder.img_medal!!.setImageResource(R.drawable.ic_medal_silver)
            "3"->holder.img_medal!!.setImageResource(R.drawable.ic_medal_platinum)
            "4"->holder.img_medal!!.setImageResource(R.drawable.ic_medal_bronze)
            else -> holder.img_medal!!.setImageResource(R.drawable.ic_medal_silver)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    interface OnSurveySayaAdapter{
        fun onSurveyClick(model: SurveyModel)
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_date: TextView? = null
        internal var tv_month_year: TextView? = null
        internal var card_survey: CardView? = null
        internal var timeline: TimelineView? = null
        internal var tv_title: TextView? = null
        internal var tv_desc: TextView? = null
        internal var img_medal: ImageView? = null
        init {
            this.setIsRecyclable(false)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_month_year = itemView.findViewById(R.id.tv_month_year)
            tv_desc = itemView.findViewById(R.id.tv_desc)
            card_survey = itemView.findViewById(R.id.card_survey)
            timeline = itemView.findViewById(R.id.timeline)
            img_medal = itemView.findViewById(R.id.img_medal)

            timeline!!.initLine(viewType)
        }
    }
}