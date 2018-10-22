package online.ahndwon.criminalintentkotlin.models

import java.text.SimpleDateFormat
import java.util.*


class Crime {
    val mId = UUID.randomUUID()
    var mTitle: String? = null
    val mDate = Date()
    var mSolved: Boolean = false

    fun ismSolved(): Boolean {
        return mSolved
    }

    fun setmSolved(mSolved: Boolean) {
        this.mSolved = mSolved
    }

    fun getmDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, MMM d, YYYY", Locale.US)
        return dateFormat.format(mDate)
    }

}
