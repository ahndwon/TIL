package online.ahndwon.criminalintentkotlin.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.fragment_crime_list.view.*
import kotlinx.android.synthetic.main.list_item_crime.view.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.models.CrimeLab

class CrimeListFragment : Fragment() {
    private var mAdapter: CrimeAdapter? = null
    private var isSubtitleVisible = false
    var onCrimeSelected : ((Crime) -> Unit)? = null

    private val adapter = CrimeAdapter(emptyList())


    companion object {
        const val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }

    interface Callbacks {
        fun onCrimeSelected(crime: Crime)
    }

    private inner class CrimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCrime(crime: Crime) {
            itemView.setOnClickListener {
                val intent = CrimePagerActivity.newIntent(itemView.context, crime.mId)
                startActivity(intent)
            }
            crime.mTitle?.let {
                itemView.titleTextView.text = it
            }

            itemView.dateTextView.text = crime.getmDate()
            itemView.checkBox.isChecked = crime.ismSolved()

            itemView.setOnClickListener { onCrimeSelected?.invoke(crime) }
        }

    }

    private inner class CrimeAdapter(private var mCrimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

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

        fun setCrimes(crimes: List<Crime>) {
            mCrimes = crimes
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        view.crimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        if (savedInstanceState != null) {
            isSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }

        updateUI()

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, isSubtitleVisible)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        val crimes = CrimeLab.crimes
        adapter.setCrimes(crimes)
        view?.crimeRecyclerView?.adapter = adapter
        view?.crimeRecyclerView?.adapter?.notifyDataSetChanged()

        view?.crimeRecyclerView?.layoutManager = LinearLayoutManager(view?.context)
        updateSubtitle()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu.findItem(R.id.menu_item_show_subtitle)
        if (isSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_new_crime -> {
                val crime = Crime()
                CrimeLab.crimes.add(crime)
//                activity?.let {
//                    val intent = CrimePagerActivity.newIntent(it, crime.mId)
//                    startActivity(intent)
//                    return true
//                }
                updateUI()
                return true
            }

            R.id.menu_item_show_subtitle-> {
                isSubtitleVisible = !isSubtitleVisible
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateSubtitle() {
        val crimeCount = CrimeLab.crimes.size
//        var subTitle = getString(R.string.subtitle_format, crimeCount.toString())
        var subTitle = resources.getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount)

        if (!isSubtitleVisible) {
            subTitle = ""
        }

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.subtitle = subTitle
    }
}

