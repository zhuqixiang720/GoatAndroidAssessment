package com.goat.assessment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.goat.assessment.R
import com.goat.assessment.databinding.MainActivityBinding
import com.goat.assessment.databinding.MainFragmentBinding
import com.goat.assessment.ui.main.MainFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var binding: MainActivityBinding
    lateinit var navController: NavController

    override fun supportFragmentInjector() = dispatchingAndroidFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.container)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.container).navigateUp()
}
