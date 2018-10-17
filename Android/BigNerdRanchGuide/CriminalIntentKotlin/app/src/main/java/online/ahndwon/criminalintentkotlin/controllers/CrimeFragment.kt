package online.ahndwon.criminalintentkotlin.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.fragment_crime.view.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import java.util.*


class CrimeFragment : Fragment() {


    private var mCrime = Crime()
    companion object {
        private const val ARG_CRIME_ID = "crime_id"

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        view.crime_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mCrime.setmTitle(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        view.crime_date.text = mCrime.getmDate()
        view.crime_date.isEnabled = false

        view.crime_solved.setOnCheckedChangeListener { buttonView, isChecked -> mCrime.setmSolved(isChecked) }

        return view

    }

}
