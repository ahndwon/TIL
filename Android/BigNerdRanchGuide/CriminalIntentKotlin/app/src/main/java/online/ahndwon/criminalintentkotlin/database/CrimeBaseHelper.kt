package online.ahndwon.criminalintentkotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import online.ahndwon.criminalintentkotlin.database.CrimeDbSchema.Companion.CrimeTable
import online.ahndwon.criminalintentkotlin.database.CrimeDbSchema.Companion.Cols

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "crimeBase.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + CrimeTable.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    Cols.UUID + ", " +
                    Cols.TITLE + ", " +
                    Cols.DATE + ", " +
                    Cols.SOLVED
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}