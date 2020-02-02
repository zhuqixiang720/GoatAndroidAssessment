package com.goat.assessment.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.goat.assessment.R
import com.goat.assessment.databinding.MainFragmentBinding
import com.goat.assessment.di.Injectable
import com.goat.assessment.service.ServiceRepository
import com.goat.assessment.ui.details.DetailsFragment
import com.goat.assessment.utils.observeNonNull
import javax.inject.Inject

private const val LOCATION_UPDATE_INTERVAL_MS = 30000L
private const val REQUEST_LOCATION_PERMISSION = 10101

private val LOG_TAG = MainFragment::class.java.simpleName

class MainFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var serviceRepository: ServiceRepository

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var viewModel: MainViewModel

    private val locationListener = object : LocationListener {
        override fun onProviderDisabled(provider: String?) {
            Log.w(LOG_TAG, "locationListener#onProviderDisabled = $provider")
        }

        override fun onProviderEnabled(provider: String?) {
            Log.w(LOG_TAG, "locationListener#onProviderEnabled = $provider")
        }

        override fun onLocationChanged(location: Location?) {
            Log.w(LOG_TAG, "locationListener#onLocationChanged = $location")
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.w(LOG_TAG, "locationListener#onStatusChanged provider:$provider, status:$status")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get()

        val binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d(LOG_TAG, "Refresh the current weather")
            viewModel.fetchWeatherForecast(getCurrentLocation())
        }

        viewModel.serviceError.observeNonNull(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        viewModel.detailsPageEvent.observeNonNull(viewLifecycleOwner) {
            navigateToDetails()
        }

        return binding.root
    }

    private fun navigateToDetails() {
        if (viewModel.weatherHourlyInfo == null) {
            Toast.makeText(requireContext(), "Weather Hourly Data is not available", Toast.LENGTH_SHORT).show()
        } else {
           // TODO: navigate to next fragment
        }
    }

    override fun onStart() {
        super.onStart()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            requestForLocationPermission()
        } else {
            // Permission is granted, get the current location
            viewModel.fetchWeatherForecast(getCurrentLocation())
        }
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_INTERVAL_MS,
                0F,
                locationListener
            )
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                LOCATION_UPDATE_INTERVAL_MS,
                0F,
                locationListener
            )
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    private fun requestForLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        requestPermissions(permissions, REQUEST_LOCATION_PERMISSION)
    }

    private fun getCurrentLocation(): Location? {
        var location: Location? = null
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        Log.d(LOG_TAG, "Get current location from NetworkProvider = $location")
        if (location == null) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }

        Log.d(LOG_TAG, "Get current location GPSProvider = $location")

        return location
    }

    private fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL_MS, 0F, locationListener)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //  permission was granted, get the location data
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL_MS, 0F, locationListener)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    requestLocationUpdates()
                }
                viewModel.fetchWeatherForecast(getCurrentLocation())
            }
        }
    }
}
