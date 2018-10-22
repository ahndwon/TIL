package online.ahndwon.criminalintentkotlin.controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_crime_pager.*
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.models.CrimeLab
import java.util.*

class CrimePagerActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        val crimes = CrimeLab.crimes
        val fragmentManger = supportFragmentManager
        viewPager?.adapter = object : FragmentPagerAdapter(fragmentManger) {
            override fun getItem(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(crime.mId)
            }

            override fun getCount(): Int {
                return crimes.size
            }
        }


        for (i in crimes.indices) {
            if (crimes[i].mId == crimeId) {
                viewPager.currentItem = i
                break
            }
        }
    }

    companion object {
        private val EXTRA_CRIME_ID = "online.ahndwon.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }
}
