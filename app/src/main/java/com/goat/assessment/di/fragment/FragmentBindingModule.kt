package com.goat.assessment.di.fragment

import com.goat.assessment.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module for creating sub-component for Fragments and injecting them.
 */
@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}