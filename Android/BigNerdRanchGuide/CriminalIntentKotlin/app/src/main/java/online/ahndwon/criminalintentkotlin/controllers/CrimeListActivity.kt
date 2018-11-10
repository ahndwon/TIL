package online.ahndwon.criminalintentkotlin.controllers

import android.support.v4.app.Fragment
import android.widget.FrameLayout
import online.ahndwon.criminalintentkotlin.R
import online.ahndwon.criminalintentkotlin.models.Crime

class CrimeListActivity : SingleFragmentActivity() {

    private val onCrimeSelected : ((Crime) -> Unit) = { crime ->
        if (findViewById<FrameLayout>(R.id.detail_fragment_container) == null) {
            val intent = CrimePagerActivity.newIntent(this, crime.mId)
            startActivity(intent)
        } else {
            val newDetail = CrimeFragment.newInstance(crime.mId)
            newDetail.onCrimeUpdate = this@CrimeListActivity.onCrimeUpdate

            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_container, newDetail)
                .commit()
        }
    }

    private val onCrimeUpdate : ((Crime) -> Unit) = { crime ->
        val listFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as CrimeListFragment?
        listFragment?.updateUI()
    }

    override fun createFragment(): Fragment {
        return CrimeListFragment().apply {
            this.onCrimeSelected = this@CrimeListActivity.onCrimeSelected
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_masterdetail
    }
}
