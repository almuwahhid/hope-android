package id.co.hope.app.greeting.helper

import id.co.hope.R
import id.co.hope.app.greeting.model.GreetingModel

class GreetingHelper {
    companion object{
        fun greetingData(): MutableList<GreetingModel>{
            var greetingList: MutableList<GreetingModel> = ArrayList()
            var greetingModel = GreetingModel("SURVEY MENGENAI KEINGINAN KARIRMU",
                "Survey yang Anda lakukan pada aplikasi ini akan menunjukkan pengetahuan diri Anda dalam mengetahui dan mencapai  karir yang diinginkan.",
                            R.drawable.slider1)
            greetingList.add(greetingModel)

            greetingModel = GreetingModel("KEGIATANKU",
                "Feature ini akan memberikan serangkaian kegiatan yang dapat membantu Anda dalam melakukan eksplorasi karir serta komitmen Anda dalam mencapai karir yang diinginkan.",
                            R.drawable.slider2)
            greetingList.add(greetingModel)

            greetingModel = GreetingModel("ARTIKEL KARIR",
                "Feature ini menyediakan bacaan mengenai informasi, tren dan perkembangan karir saat ini.",
                R.drawable.slider3)
            greetingList.add(greetingModel)

            return greetingList
        }
    }
}