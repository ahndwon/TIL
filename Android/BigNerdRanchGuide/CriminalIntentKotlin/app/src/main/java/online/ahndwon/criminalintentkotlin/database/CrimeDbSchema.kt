package online.ahndwon.criminalintentkotlin.database

class CrimeDbSchema {
    companion object {
        class CrimeTable {
            companion object {
                const val NAME = "crimes";
            }
        }

        class Cols {
            companion object {
                const val UUID = "uuid"
                const val TITLE = "title"
                const val DATE = "date"
                const val SOLVED = "solved"

            }
        }
    }
}