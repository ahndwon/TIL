package online.ahndwon.criminalintentkotlin.controllers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_crime.view.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import java.util.*


class CrimeFragment : Fragment() {


    private var mCrime = Crime()
    companion object {
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const val REQUEST_DATE = 0

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
                mCrime.mTitle = s.toString()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        updateDate()
        view.crimeDateButton.setOnClickListener { _ ->
//            DatePickerFragment().show(fragmentManager, DIALOG_DATE)
            DatePickerFragment.newInstance(mCrime.mDate).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            }.show(fragmentManager, DIALOG_DATE)
        }

        view.crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.setmSolved(isChecked) }

        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime.mDate = date
            updateDate()
        }
    }

    private fun updateDate() {
        view?.crimeDateButton?.text = mCrime.getmDate()
    }
}
