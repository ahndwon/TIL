package online.ahndwon.criminalintentkotlin.database

import android.database.Cursor
import android.database.CursorWrapper
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.database.CrimeDbSchema.Companion.Cols
import java.util.*


class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime(): Crime? {
        val uuidString = getString(getColumnIndex(Cols.UUID))
        val title = getString(getColumnIndex(Cols.TITLE))
        val date = getLong(getColumnIndex(Cols.DATE))
        val isSolved = getInt(getColumnIndex(Cols.SOLVED))
        val suspect = getString(getColumnIndex(Cols.SUSPECT))

        val crime = Crime(UUID.fromString(uuidString))
        crime.mTitle = title
        crime.mDate = Date(date)
        crime.mSolved = isSolved != 0
        crime.suspect = suspect

        return crime
    }
}