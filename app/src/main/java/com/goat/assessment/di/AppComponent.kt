package com.goat.assessment.di

import android.app.Application
import com.goat.assessment.SampleApplication
import com.goat.assessment.di.activity.ActivityBindingModule
import com.goat.assessment.di.fragment.FragmentBindingModule
import com.goat.assessment.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        NetworkModule::class,
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: SampleApplication)
}
