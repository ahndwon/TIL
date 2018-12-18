package online.ahndwon.photogallery

import android.content.Context
import android.preference.PreferenceManager

class QueryPreferences {
    companion object {
        val PREF_SEARCH_QUERY = "searchQuery"
        val PREF_LAST_RESULT_ID = "lastResultId"

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
