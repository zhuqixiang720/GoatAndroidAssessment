package com.goat.assessment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.goat.assessment.databinding.DetailsFragmentBinding
import com.goat.assessment.di.Injectable
import javax.inject.Inject

class DetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DetailsFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        val hourlyInfo =  DetailsFragmentArgs.fromBundle(arguments!!).hourlyInfo
        binding.hourlyRecyclerView.adapter = DetailsHourlyAdapter(resources, hourlyInfo)

        val layoutManager = LinearLayoutManager(context)
        binding.hourlyRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.hourlyRecyclerView.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

}
