package online.ahndwon.criminalintentkotlin.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_crime_list.view.*
import kotlinx.android.synthetic.main.list_item_crime.view.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.models.CrimeLab


class CrimeListFragment : Fragment() {
    private var mAdapter: CrimeAdapter? = null

    private inner class CrimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCrime(crime: Crime) {
            itemView.setOnClickListener {
                val intent = CrimePagerActivity.newIntent(itemView.context, crime.mId)
                startActivity(intent)
            }
            itemView.titleTextView.text = crime.mTitle
            itemView.dateTextView.text = crime.getmDate()
            itemView.checkBox.isChecked = crime.ismSolved()
        }

    }

    private inner class CrimeAdapter(private val mCrimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(activity)
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(crimeHolder: CrimeHolder, position: Int) {
            Log.d("list", "$position")
            val crime = mCrimes[position]
            crimeHolder.bindCrime(crime)
        }

        override fun getItemCount(): Int {
            return mCrimes.count()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        view.crimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        updateUI(view)

        return view
    }

    private fun updateUI(view: View) {
        val crimes = CrimeLab.crimes
        val adapter = CrimeAdapter(crimes)
        view.crimeRecyclerView.adapter = adapter
        view.crimeRecyclerView.layoutManager = LinearLayoutManager(view.context)
    }
}
