package id.ac.uny.riset.ride.data.model

import java.io.Serializable

data class AspekIndikatorModel(var id_indikator: String,
                               var nama_indikator: String,
                               var id_aspek: String,
                               var plus_comment: String,
                               var negative_comment: String,
                               var nama_aspek: String,
                               var urutan: Int,
                               var pernyataan: List<PernyataanModel>
                                ): Serializable {
}