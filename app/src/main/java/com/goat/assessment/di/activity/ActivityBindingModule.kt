package com.goat.assessment.di.activity

import com.goat.assessment.MainActivity
import com.goat.assessment.di.fragment.FragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeMainActivity(): MainActivity
}