package id.co.hope.app.main.checklistjournal.Adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel
import id.co.hope.R
import id.co.hope.utils.HopeFunction

class ChecklistJournalAdapter(context: Context, list_intervensi: MutableList<TaskPertanyaanModel>, private val onChecklistJournalAdapter: OnChecklistJournalAdapter) : RecyclerView.Adapter<ChecklistJournalAdapter.Holder>() {

    lateinit var list_intervensi: MutableList<TaskPertanyaanModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list_intervensi = list_intervensi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistJournalAdapter.Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_checklist_journal, parent, false)
        return ChecklistJournalAdapter.Holder(layoutView)
    }

    override fun getItemCount(): Int {
        return list_intervensi.size
    }

    override fun onBindViewHolder(holder: ChecklistJournalAdapter.Holder, position: Int) {
        val intervensiModel = list_intervensi.get(position)
        holder.card_lay!!.setOnClickListener({
            onChecklistJournalAdapter.onPertanyaanClick(intervensiModel)
        })
        holder.tv_content!!.setText(intervensiModel.intervensi_task)
        holder.tv_date!!.setText(HopeFunction.parseTimestampToSimpleDate(intervensiModel.tanggal_task))
        holder.tv_month_year!!.setText(HopeFunction.parseTimestampToSimpleMonthYear(intervensiModel.tanggal_task))

        holder.tv_month_year!!.visibility = View.GONE
        holder.tv_date!!.setText(""+(position+1))

        Log.d("adapters1 ", intervensiModel.status_task)
        if(intervensiModel.status_task.equals("N")){
            holder.tv_time!!.visibility = View.GONE
            Log.d("adapters ", HopeFunction.isToday(intervensiModel.tanggal_task))
            /*if(HopeFunction.isToday(intervensiModel.tanggal_task).equals("today")){
                holder.img_check!!.setImageResource(R.drawable.ic_play_rounded_button);
            } else if(HopeFunction.isToday(intervensiModel.tanggal_task).equals("yesterday")){
                holder.img_check!!.setImageResource(R.drawable.ic_error);
            } else if(HopeFunction.isToday(intervensiModel.tanggal_task).equals("tomorrow")){
                holder.img_check!!.setImageResource(R.drawable.ic_stop);
            }*/
            holder.img_check!!.setImageResource(R.drawable.ic_play_rounded_button);
        } else {
            Log.d("adapters2 ", intervensiModel.status_task)

            if (intervensiModel.status_task == "T") {
                holder.img_check!!.setImageResource(R.drawable.ic_success_grey)
            } else {
                holder.img_check!!.setImageResource(R.drawable.ic_success)
            }

            holder.tv_time!!.visibility = View.VISIBLE
            try {
//                holder.tv_time!!.setText(intervensiModel.tanggal_submit.split(" ")[1])
                holder.tv_time!!.setText(HopeFunction.parseTimestampToSimpleFullDateTime(intervensiModel.tanggal_submit))
            } catch (e: Exception){
                e.printStackTrace()
            }
//            holder.img_check!!.setColorFilter(ContextCompat.getColor(context, R.color.blue_400), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_content: TextView? = null
        internal var tv_date: TextView? = null
        internal var tv_month_year: TextView? = null
        internal var img_check: ImageView? = null
        internal var tv_time: TextView? = null
        internal var card_lay: CardView? = null
        init {
            this.setIsRecyclable(false)
            tv_content = itemView.findViewById(R.id.tv_content)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_month_year = itemView.findViewById(R.id.tv_month_year)
            img_check = itemView.findViewById(R.id.img_check)
            tv_time = itemView.findViewById(R.id.tv_time)
            card_lay = itemView.findViewById(R.id.card_lay)

        }
    }

    interface OnChecklistJournalAdapter{
        fun onPertanyaanClick(intervensiModel: TaskPertanyaanModel)
    }
}
