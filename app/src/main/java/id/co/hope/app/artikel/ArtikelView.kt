package id.co.hope.app.artikel

import id.co.hope.app.artikel.model.ArtikelModel
import lib.almuwahhid.base.BaseView

interface ArtikelView {
    interface Presenter{
        fun requestArtikel()
    }
    interface View: BaseView {
        fun onRequestArtikel(list: MutableList<ArtikelModel>)
        fun onEmptyData(message: String)
    }
}