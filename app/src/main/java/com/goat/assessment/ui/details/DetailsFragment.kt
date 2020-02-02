package com.goat.assessment.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.goat.assessment.api.model.WeatherInfoResponse
import com.goat.assessment.databinding.DetailsFragmentBinding
import com.goat.assessment.databinding.MainFragmentBinding
import com.goat.assessment.di.Injectable
import javax.inject.Inject

private const val KEY_HOURLY_INFO = "KEY_HOURLY_INFO"

class DetailsFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(hourlyInfo: WeatherInfoResponse): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle().apply {
                putParcelable(KEY_HOURLY_INFO, hourlyInfo)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val hourlyInfo = arguments?.get(KEY_HOURLY_INFO) as WeatherInfoResponse
        viewModel = ViewModelProvider(this, viewModelFactory).get()
        viewModel.setHourlyInfo(hourlyInfo)

        val binding = DetailsFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        return binding.root
    }

}
