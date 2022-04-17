package id.co.hope.app.survey.helper

import id.ac.uny.riset.ride.data.model.AspekIndikatorModel
import id.ac.uny.riset.ride.data.model.NilaiPernyataanModel
import id.ac.uny.riset.ride.data.model.PernyataanModel
import id.co.hope.app.survey.model.PertanyaanSurveyModel

class SurveyHelper {
    companion object{
        fun getListPertanyaan(id_survey : String, pernyataanModel : PernyataanModel): MutableList<PertanyaanSurveyModel>? {
            var getListPertanyaan : MutableList<PertanyaanSurveyModel>? = ArrayList()
            var data = PertanyaanSurveyModel()

            for (nilaiPernyataanModel in pernyataanModel.nilai){
                data = PertanyaanSurveyModel(""+nilaiPernyataanModel.nilai_pernyataan, nilaiPernyataanModel.nama_nilai, id_survey, pernyataanModel, nilaiPernyataanModel)
                getListPertanyaan!!.add(data)
            }
            return getListPertanyaan
        }

        fun getClickedPertanyaan(getListPertanyaan : MutableList<PertanyaanSurveyModel>) : PertanyaanSurveyModel{
            for(pertanyaan in getListPertanyaan){
                if(pertanyaan.isClicked)
                    return pertanyaan
            }
            return PertanyaanSurveyModel()
        }

        fun convertFromIndikatorToPernyataan(list: List<AspekIndikatorModel>): MutableList<PernyataanModel> {
            val convertFromIndikatorToPernyataan = ArrayList<PernyataanModel>()

            for (aspekIndikatorModel in list) {
                for (pernyataanModel in aspekIndikatorModel.pernyataan) {
                    convertFromIndikatorToPernyataan.add(pernyataanModel)
                }
            }
            return convertFromIndikatorToPernyataan
        }
    }
}