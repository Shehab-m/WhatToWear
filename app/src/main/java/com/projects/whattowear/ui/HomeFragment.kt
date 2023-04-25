package com.projects.whattowear.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projects.whattowear.databinding.FragmentHomeBinding
import com.projects.whattowear.local.PrefsUtil.Companion.initPrefs
import com.projects.whattowear.repository.DaysRepositoryImpl
import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiClient
import com.projects.whattowear.network.DataManager

class HomeFragment : Fragment(), HomeView {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var data: DataManager
    private lateinit var presenter: HomePresenter
    private lateinit var firstDay: Interval
    private lateinit var homeAdapter: DaysAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initPrefs(requireActivity())
        initPresenter()
        initViews()

        return binding.root
    }

    private fun initPresenter() {
        val apiClient = ApiClient()
        val repository = DaysRepositoryImpl(apiClient)
        presenter = HomePresenter(repository)
        presenter.homeView = this
        presenter.initView()
    }

    private fun initViews() {
        data = DataManager()
        homeAdapter = DaysAdapter(::setupBinding)
        binding.recyclerViewDays.adapter = homeAdapter

    }

    @SuppressLint("SetTextI18n")
    private fun setupBinding(today: Interval) {

            with(binding){
                textDayDate.text = data.getDayName(today.startTime.substringBefore("T"), "EEEE")
                imageWeather.setImageResource(today.weatherImageId)
                textDegree.text = "${today.values.temperatureAvg}°c"
                imageClothes.setImageResource(today.clothesImageId)
                textOurPick.text = if (today.startTime == firstDay.startTime) {
                    "Here is our pick for you today"
                } else {
                    "Here is our pick for your ${data.getDayName(today.startTime, "EEEE")}"
                }
                textToday.text = if (today.startTime == firstDay.startTime) {
                    "Today"
                } else {
                    data.getDayName(today.startTime.substringBefore("T"), "EEEE").apply {
                        textDayDate.text = today.startTime.substringBefore("T")
                    }
                }
                textLocation.visibility = View.VISIBLE
                materialCardView.visibility = View.VISIBLE
                progressbar.visibility = View.INVISIBLE
            }

    }

    override fun getIntervals(intervals: List<Interval>) {
            firstDay = intervals[0]
            homeAdapter.submitList(intervals)
            val today = intervals[0]
            setupBinding(today)
    }

    override fun getErrorMessage(message: String) {
            with(binding){
                textError.text = message
                progressbar.visibility = View.INVISIBLE
            }
    }


    override fun onDestroy() {
        super.onDestroy()

    }


}