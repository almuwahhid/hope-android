package id.ac.uny.riset.ride.data.model

import java.io.Serializable

data class NilaiPernyataanModel(var id_nilai_jenis_pernyataan : String,
                                var nilai_pernyataan: Int = 0,
                                var nama_nilai: String = "") : Serializable