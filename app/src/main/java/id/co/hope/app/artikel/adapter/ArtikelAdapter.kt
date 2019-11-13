package id.co.hope.app.artikel.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.hope.BuildConfig
import id.co.hope.R
import id.co.hope.app.artikel.model.ArtikelModel
import id.co.hope.utils.BitmapTransform
import id.co.hope.utils.HopeFunction
import id.co.hope.utils.avatarview.AvatarPlaceholder
import id.co.hope.utils.avatarview.loader.PicassoLoader

class ArtikelAdapter (context: Context, list: MutableList<ArtikelModel>, private val onAdapterArtikel: OnAdapterArtikel) : RecyclerView.Adapter<ArtikelAdapter.Holder>() {

    lateinit var list: MutableList<ArtikelModel>
    lateinit var context: Context

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelAdapter.Holder {
        val layoutView: View
        layoutView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_artikel, parent, false)
        return ArtikelAdapter.Holder(layoutView, viewType)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ArtikelAdapter.Holder, position: Int) {
        val artikelModel = list.get(position)
        holder.apply {
            tv_date!!.setText(HopeFunction.parseDateToRealDate(artikelModel.tgl_artikel))
            tv_judul!!.setText(artikelModel.judul_artikel)
            tv_url!!.setText(artikelModel.url_artikel)
            card_artikel!!.setOnClickListener({
                onAdapterArtikel.onArtikelClick(artikelModel)
            })

            val imageLoader = PicassoLoader()
            val refreshableAvatarPlaceholder = AvatarPlaceholder(artikelModel.judul_artikel)
            Picasso.with(context)
                .load(BuildConfig.base_url+"datas/artikel/"+ artikelModel.foto_artikel)
                .placeholder(refreshableAvatarPlaceholder)
                .transform(BitmapTransform())
                .into(foto_artikel)
        }

    }

    interface OnAdapterArtikel{
        fun onArtikelClick(model: ArtikelModel)
    }

    class Holder (itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        internal var tv_date: TextView? = null
        internal var tv_judul: TextView? = null
        internal var tv_url: TextView? = null
        internal var card_artikel: CardView? = null
        internal var foto_artikel: ImageView? = null

        init {
            this.setIsRecyclable(false)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_judul = itemView.findViewById(R.id.tv_judul)
            tv_url = itemView.findViewById(R.id.tv_url)
            foto_artikel = itemView.findViewById(R.id.foto_artikel)
            card_artikel = itemView.findViewById(R.id.card_artikel)
        }
    }
}