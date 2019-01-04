package com.example.ahndwon.draganddraw

import android.os.Bundle
import androidx.fragment.app.Fragment

class DragAndDrawActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return DragAndDrawFragment.newInstance()
    }

}
