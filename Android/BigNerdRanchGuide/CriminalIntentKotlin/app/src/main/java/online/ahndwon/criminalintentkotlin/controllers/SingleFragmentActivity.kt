package online.ahndwon.criminalintentkotlin.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import online.ahndwon.criminalintentkotlin.R

abstract class SingleFragmentActivity : FragmentActivity() {
    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = createFragment()
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
