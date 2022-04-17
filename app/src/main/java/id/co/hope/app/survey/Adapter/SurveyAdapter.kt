package id.co.hope.app.survey.Adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.co.hope.R
import id.co.hope.app.artikel.adapter.ArtikelAdapter
import id.co.hope.app.artikel.model.ArtikelModel
import id.co.hope.app.survey.model.PertanyaanSurveyModel

class SurveyAdapter (context: Context, list: MutableList<PertanyaanSurveyModel>, private val onSurveyAdapter: OnSurveyAdapter) : RecyclerView.Adapter<SurveyAdapter.Holder>() {

    lateinit var list: MutableList<PertanyaanSurveyModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_survey, parent, false)
        return Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = list.get(position)
        holder.tv_pertanyaan!!.setText(model!!.nama_nilai_pertanyaan)
        if(model!!.isClicked){
            holder.img_pertanyaan!!.setImageResource(R.drawable.ic_check_circle_primary_24dp)
        } else {
            holder.img_pertanyaan!!.setImageResource(R.drawable.ic_check_circle_black_24dp)
        }

        holder.card_pertanyaan!!.setOnClickListener({
            onSurveyAdapter.onSurveyAdapter(position)
        })
    }


    interface OnSurveyAdapter{
        fun onSurveyAdapter(position: Int)
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_pertanyaan: TextView? = null
        internal var img_pertanyaan: ImageView? = null
        internal var card_pertanyaan: CardView? = null

        init {
            this.setIsRecyclable(false)
            tv_pertanyaan = itemView.findViewById(R.id.tv_pertanyaan)
            img_pertanyaan = itemView.findViewById(R.id.img_pertanyaan)
            card_pertanyaan = itemView.findViewById(R.id.card_pertanyaan)
        }
    }
}