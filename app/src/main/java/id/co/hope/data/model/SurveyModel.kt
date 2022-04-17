package id.ac.uny.riset.ride.data.model

import java.io.Serializable

data class SurveyModel (var id_survey: String,
                        var id_user: String,
                        var id_status_identitas_religius: String,
                        var tanggal_survey: String,
                        var nama_status: String = "",
                        var deskripsi_status: String = "",
                        var nilai: String = "",
                        var komentar_aspek: MutableList<String> = ArrayList()
                        ) : Serializable