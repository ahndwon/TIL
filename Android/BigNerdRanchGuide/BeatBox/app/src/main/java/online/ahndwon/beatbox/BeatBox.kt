package online.ahndwon.beatbox

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.IOException

class BeatBox(context : Context) {
    val assets : AssetManager = context.assets
    val sounds = ArrayList<Sound>()

    init {
        loadSounds()
    }

    companion object {
        val TAG: String = "BeatBox"

        val SOUNDS_FOLDER: String = "sample_sounds"
    }

    private fun loadSounds() {
        try {
            val soundNames = assets.list(SOUNDS_FOLDER)
            soundNames?.let {
                Log.i(TAG, "Found ${it.size} sounds")

                for (fileName in it) {
                    val assetPath = "$SOUNDS_FOLDER/$fileName"
                    sounds.add(Sound(assetPath))
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Could not list assets", e)
        }


    }

}