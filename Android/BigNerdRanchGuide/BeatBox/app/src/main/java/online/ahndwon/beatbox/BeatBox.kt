package online.ahndwon.beatbox

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

class BeatBox(context: Context) {
    val assets: AssetManager = context.assets
    val soundPool: SoundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)
    val sounds = ArrayList<Sound>()

    init {
        loadSounds()
    }

    companion object {
        val TAG: String = BeatBox::class.java.name
        const val SOUNDS_FOLDER: String = "sample_sounds"
        const val MAX_SOUNDS = 5
    }

    private fun loadSounds() {
        try {
            val soundNames = assets.list(SOUNDS_FOLDER)
            soundNames?.let {
                Log.i(TAG, "Found ${it.size} sounds")

                for (fileName in it) {
                    val assetPath = "$SOUNDS_FOLDER/$fileName"
                    val sound = Sound(assetPath)
                    load(sound)
                    sounds.add(sound)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Could not list assets", e)
        }


    }

    @Throws(IOException::class)
    private fun load(sound: Sound) {
        val afd = assets.openFd(sound.assetPath)
        sound.soundId = soundPool.load(afd, 1)
    }

    fun play(sound: Sound) {
        val soundId = sound.soundId
        soundId?.let {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }

}