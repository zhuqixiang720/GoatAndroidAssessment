package com.goat.assessment.ui.details

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goat.assessment.api.model.WeatherInfoDataResponse
import com.goat.assessment.api.model.WeatherInfoResponse
import com.goat.assessment.databinding.ListItemHourlyBinding
import com.goat.assessment.databinding.ListItemHourlyHeaderBinding

private const val VIEW_TYPE_HOURLY_HEADER = 101
private const val VIEW_TYPE_HOURLY_ITEM = 102

class DetailsHourlyAdapter(
    private val resources: Resources,
    private val weatherInfo: WeatherInfoResponse
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HOURLY_HEADER -> {
                HourlyHeaderViewHolder.createViewHolder(inflater, parent)
            }
            VIEW_TYPE_HOURLY_ITEM -> {
                HourlyItemViewHolder.createViewHolder(inflater, parent)
            }
            else -> throw IllegalArgumentException("View type is not supported.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_HOURLY_HEADER -> (holder as HourlyHeaderViewHolder).bind(resources, weatherInfo)
            VIEW_TYPE_HOURLY_ITEM -> (holder as HourlyItemViewHolder).bind(weatherInfo.data[position])
            else -> throw IllegalArgumentException("View type is not supported.")
        }
    }

    override fun getItemCount(): Int {
        return 24 + 1    // 24 hours + header
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_HOURLY_HEADER
        }
        return VIEW_TYPE_HOURLY_ITEM
    }

    //
    // ---------------------------------------------------------------------------------
    // View holders for each of the view types are defined below
    // ---------------------------------------------------------------------------------
    //

    /**
     * A viewholder for header item.
     */
    private class HourlyHeaderViewHolder internal constructor(
        private val headerBinding: ListItemHourlyHeaderBinding
    ) : RecyclerView.ViewHolder(headerBinding.root) {
        companion object {
            fun createViewHolder(
                inflater: LayoutInflater,
                parent: ViewGroup
            ): HourlyHeaderViewHolder {
                val headerBinding = ListItemHourlyHeaderBinding.inflate(inflater, parent, false)
                return HourlyHeaderViewHolder(headerBinding)
            }
        }

        internal fun bind(resources: Resources, hourlyInfo: WeatherInfoResponse) {
            val hourlyHeaderViewModel = DetailsHourlyHeaderViewModel(resources)
            hourlyHeaderViewModel.setWeatherInfo(hourlyInfo)
            headerBinding.vm = hourlyHeaderViewModel
        }
    }

    /**
     * A viewholder for hourly item.
     */
    private class HourlyItemViewHolder internal constructor(
        private val itemBinding: ListItemHourlyBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        companion object {
            fun createViewHolder(
                inflater: LayoutInflater,
                parent: ViewGroup
            ): HourlyItemViewHolder {
                val itemBinding = ListItemHourlyBinding.inflate(inflater, parent, false)
                return HourlyItemViewHolder(itemBinding)
            }
        }

        internal fun bind(hourlyItemInfo: WeatherInfoDataResponse) {
            val hourlyItemViewModel = DetailsHourlyItemViewModel()
            hourlyItemViewModel.seItemInfo(hourlyItemInfo)
            itemBinding.vm = hourlyItemViewModel
        }
    }
}