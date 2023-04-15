package com.projects.whattowear.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.whattowear.R
import com.projects.whattowear.databinding.ItemDayBinding
import com.projects.whattowear.model.DayWeatherType
import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiClient
import com.projects.whattowear.network.DataManager
import com.projects.whattowear.network.NetworkUtils

class DaysAdapter(
    private val listener: (item: Interval) -> Unit,
) :
    ListAdapter<Interval, DaysAdapter.ItemViewHolder>(DaysDiffUtil()) {
    private val data = DataManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listener, getItem(position))
    }


    inner class ItemViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(listener: (item: Interval) -> Unit, interval: Interval) {
            binding.apply {
                textTemperatureMax.text = "${interval.values.temperatureAvg}°c"
                textItemDayDate.text =
                    "${
                        data.getDayName(
                            interval.startTime.substringBefore("T"),
                            "EEE"
                        )
                    }\n${interval.startTime.substring(5, 10)}"
                imageWeather.setImageResource(interval.weatherImageId)
                textWeatherType.text = when (interval.weatherType) {
                    DayWeatherType.HOT -> "Sunny"
                    DayWeatherType.COLD -> "Cold"
                    else -> "Worm"
                }
                root.setOnClickListener { listener(interval) }
            }
        }

    }

    class DaysDiffUtil() : DiffUtil.ItemCallback<Interval>() {
        override fun areItemsTheSame(oldItem: Interval, newItem: Interval): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Interval, newItem: Interval): Boolean {
            return oldItem == newItem
        }
    }
}