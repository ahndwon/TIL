package online.ahndwon.photogallery

import android.content.Context
import android.preference.PreferenceManager

class QueryPreferences {
    companion object {
        val PREF_SEARCH_QUERY = "searchQuery"
        val PREF_LAST_RESULT_ID = "lastResultId"
        val PREF_IS_ALARM_ON = "isAlarmOn"

        fun isAlarmOn(context: Context): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false)
        }

        fun setAlarmOn(context: Context, isOn: Boolean) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply()
        }

        fun getStoredQuery(context: Context): String? {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null)
        }

        fun setStoredQuery(context: Context, query: String?) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply()
        }

        fun getLastResultId(context: Context): String? {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null)
        }

        fun setLastResultId(context: Context, lastResultId: String) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply()
        }
    }
}