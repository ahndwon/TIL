package online.ahndwon.beatbox

import android.support.v4.app.Fragment

class BeatBoxActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return BeatBoxFragment.newInstance()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_beat_box)
//    }
}
