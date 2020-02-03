package com.goat.assessment.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goat.assessment.ui.details.DetailsHourlyHeaderViewModel
import com.goat.assessment.ui.details.DetailsHourlyItemViewModel
import com.goat.assessment.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsHourlyHeaderViewModel::class)
    abstract fun bindDetailsHourlyHeaderViewModel(viewModel: DetailsHourlyHeaderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsHourlyItemViewModel::class)
    abstract fun bindDetailsHourlyItemViewModel(viewModel: DetailsHourlyItemViewModel): ViewModel
}