package com.goat.assessment

import android.app.Activity
import android.app.Application
import android.util.Log
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import com.goat.assessment.di.AppInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

private val LOG_TAG = SampleApplication::class.java.simpleName

/**
 * Global application class for the Android app
 */
class SampleApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        initDependencyInjection()

        setupRx()
    }

    private fun initDependencyInjection() {
        // Initialize dagger.
        AppInjector.init(this)
    }

    private fun setupRx() {
        RxJavaPlugins.setErrorHandler { error ->
            Log.e(LOG_TAG, "Uncaught RXJava exception ${error.localizedMessage}")
        }
    }
}
