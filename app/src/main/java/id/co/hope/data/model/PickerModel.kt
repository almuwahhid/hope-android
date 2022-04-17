package id.ac.uny.riset.ride.data.model

import java.io.Serializable

data class PickerModel (
        var id: String = "",
        var name: String = "",
        var other: String = "",
        var isClicked: Boolean = false
): Serializable {
    constructor(id: String, agama: String) : this() {
        this.id = id
        name = agama
    }
    constructor(id: String, agama: String, other: String) : this() {
        this.id = id
        name = agama
        this.other = other
    }

}