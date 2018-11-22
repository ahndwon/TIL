package online.ahndwon.nerdlauncher

import android.os.Bundle
import androidx.fragment.app.Fragment

class NerdLauncherActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return NerdLauncherFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
