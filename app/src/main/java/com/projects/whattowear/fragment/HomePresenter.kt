package com.projects.whattowear.fragment

import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiCallback
import com.projects.whattowear.network.ApiClient

class HomePresenter(): ApiCallback {
    private val apiClient = ApiClient()
    private var homeView: HomeView? = null

    fun onAttach(view: HomeView) {
        homeView = view
    }

    fun onDestroy() {
        homeView = null
    }

    override fun onSuccess(intervals: List<Interval>) {
        homeView?.getIntervals(intervals)
    }

    override fun onError(message: String) {
        homeView?.getErrorMessage(message)
    }

    fun initView() {
        apiClient.makeRequest(this)
    }

}