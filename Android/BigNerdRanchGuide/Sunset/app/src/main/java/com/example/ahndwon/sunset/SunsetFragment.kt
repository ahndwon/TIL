package com.example.ahndwon.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment

class SunsetFragment : Fragment() {

    lateinit var mSceneView: View
    lateinit var mSunView: View
    lateinit var mSkyView: View

    var mBlueSkyColor = 0
    var mSunsetSkyColor = 0
    var mNightSkyColor = 0

    companion object {
        fun newInstance(): SunsetFragment {
            return SunsetFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sunset, container, false)

        mSceneView = view
        mSunView = view.findViewById(R.id.sun)
        mSkyView = view.findViewById(R.id.sky)

        mBlueSkyColor = resources.getColor(R.color.blue_sky)
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky)
        mNightSkyColor = resources.getColor(R.color.night_sky)

        mSceneView.setOnClickListener {
            startAnimation()
        }

        return view
    }

    private fun startAnimation() {
        val sunYStart = mSunView.top
        val sunYEnd = mSkyView.height

        val heightAnimator =
                ObjectAnimator.ofFloat(mSunView,
                        "y",
                        sunYStart.toFloat(),
                        sunYEnd.toFloat())
                        .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator =
                ObjectAnimator.ofInt(mSkyView,
                        "backgroundColor",
                        mBlueSkyColor,
                        mSunsetSkyColor)
                        .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator)
        animatorSet.start()

//        heightAnimator.start()
//        sunsetSkyAnimator.start()
    }
}