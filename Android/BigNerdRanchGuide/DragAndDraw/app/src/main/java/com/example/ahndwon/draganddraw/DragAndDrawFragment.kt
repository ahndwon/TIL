package com.example.ahndwon.draganddraw


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class DragAndDrawFragment : Fragment() {

    companion object {
        fun newInstance(): DragAndDrawFragment {
            return DragAndDrawFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drag_and_draw, container, false)



        return view
    }


}
