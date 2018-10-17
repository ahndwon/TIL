package online.ahndwon.criminalintentkotlin.models

import android.content.Context
import java.util.*


object CrimeLab {
    val crimes = ArrayList<Crime>().apply {
        for (i in 0..99) {
            val crime = Crime()
            crime.setmTitle("범죄 #$i")
            crime.setmSolved(i % 2 == 0)
            add(crime)
        }
    }
}
