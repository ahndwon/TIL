package online.ahndwon.criminalintentkotlin.controllers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime
import online.ahndwon.criminalintentkotlin.models.CrimeLab
import java.util.*

class CrimePagerActivity : FragmentActivity() {

    private var mViewPager: ViewPager? = null
    private var mCrimes: List<Crime>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        mViewPager = findViewById(R.id.activity_crime_pager_view_pager)

        mCrimes = CrimeLab.crimes
        val fragmentManger = supportFragmentManager
        mViewPager!!.adapter = object : FragmentPagerAdapter(fragmentManger) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes!![position]
                return CrimeFragment.newInstance(crime.getmId())
            }

            override fun getCount(): Int {
                return mCrimes!!.size
            }
        }


        for (i in mCrimes!!.indices) {
            if (mCrimes!![i].getmId().equals(crimeId)) {
                mViewPager!!.currentItem = i
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
