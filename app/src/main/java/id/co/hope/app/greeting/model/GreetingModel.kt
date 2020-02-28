package id.co.hope.app.greeting.model

import java.io.Serializable

data class GreetingModel (
    var title: String = "",
    var description: String = "",
    var img_drawable: Int = 0
): Serializable