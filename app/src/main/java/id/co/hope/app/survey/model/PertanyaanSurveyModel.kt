package id.co.hope.app.survey.model

import id.ac.uny.riset.ride.data.model.NilaiPernyataanModel
import id.ac.uny.riset.ride.data.model.PernyataanModel

data class PertanyaanSurveyModel (var nilai_pertanyaan: String = "",
                                  var nama_nilai_pertanyaan: String = "",
                                  var id_survey: String = "",
                                  var pernyataanModel: PernyataanModel? = null,
                                  var nilaiPernyataanModel: NilaiPernyataanModel? = null,
                                  var isClicked: Boolean = false){
}