package online.ahndwon.beatbox

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_beat_box.view.*
import kotlinx.android.synthetic.main.list_item_sound.view.*

class BeatBoxFragment : Fragment() {

    lateinit var beatBox: BeatBox
    lateinit var sound: Sound


    companion object {
        fun newInstance(): BeatBoxFragment {
            return BeatBoxFragment()
        }
    }

    private inner class SoundHolder(inflater: LayoutInflater, container: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.list_item_sound, container, false)
    )

    private inner class SoundAdapter(val sounds: List<Sound>) : RecyclerView.Adapter<SoundHolder>() {
//        var sounds : List<Sound> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val inflater = LayoutInflater.from(this@BeatBoxFragment.context)
            return SoundHolder(inflater, parent)
        }

        override fun getItemCount(): Int {
            return sounds.size
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]

            val button = holder.itemView.list_item_sound_button
            bindSound(button, sound)
            button.setOnClickListener {
                beatBox.play(sound)
            }

            holder.itemView.volumeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    beatBox.setVolume(sound, progress / 100f)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beat_box, container, false)
        beatBox = BeatBox(view.context)
        view.fragment_beat_box_recycler_view.layoutManager = GridLayoutManager(view.context, 3)
        view.fragment_beat_box_recycler_view.adapter = SoundAdapter(beatBox.sounds)

        return view
    }

    fun bindSound(button: Button, sound: Sound) {
        this.sound = sound
        button.text = sound.name
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }
}