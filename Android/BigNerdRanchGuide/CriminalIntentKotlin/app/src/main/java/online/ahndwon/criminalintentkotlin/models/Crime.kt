package online.ahndwon.criminalintentkotlin.models

import java.text.SimpleDateFormat
import java.util.*


class Crime {
    private val mId: UUID
    private var mTitle: String? = null
    private var mDate: Date? = null
    private var mSolved: Boolean = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }

    fun getmId(): UUID {
        return mId
    }

    fun getmTitle(): String? {
        return mTitle
    }

    fun setmTitle(mTitle: String) {
        this.mTitle = mTitle
    }


    fun setmDate(mDate: Date) {
        this.mDate = mDate
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
