package online.ahndwon.criminalintentkotlin.models

import java.text.SimpleDateFormat
import java.util.*


class Crime {
    var mId: UUID = UUID.randomUUID()
    var mTitle: String? = null
    var mDate = Date()
    var mSolved: Boolean = false
    constructor() {
        Crime(UUID.randomUUID())
    }

    constructor(id: UUID) {
        mId = id
    }


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
