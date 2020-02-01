package com.goat.assessment.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.goat.assessment.SampleApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector {
    fun init(application: SampleApplication) {
        DaggerAppComponent.builder()
            .application(application)
            .build()
            .inject(application)

        application
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    Log.d("Activity CREATE", activity.localClassName)
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                    Log.d("Activity START", activity.localClassName)
                }
                override fun onActivityResumed(activity: Activity) {
                    Log.d("Activity RESUME", activity.localClassName)
                }
                override fun onActivityPaused(activity: Activity) {
                    Log.d("Activity PAUSE", activity.localClassName)
                }
                override fun onActivityStopped(activity: Activity) {
                    Log.d("Activity STOP", activity.localClassName)
                }
                override fun onActivityDestroyed(activity: Activity) {
                    Log.d("Activity DESTROY", activity.localClassName)
                }
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                    Log.d("Activity SaveInstance", activity.localClassName)
                }
            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            fragment: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            Log.d("Fragment CREATE", fragment.toString())
                            if (fragment is Injectable) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }

                        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                            Log.d("Fragment DESTROY", f.toString())
                        }
                    }, true
                )
        }
    }
}
