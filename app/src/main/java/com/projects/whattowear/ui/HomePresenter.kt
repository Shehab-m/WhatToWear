package com.projects.whattowear.ui

import com.projects.whattowear.repository.DaysRepository
import com.projects.whattowear.repository.RepositoryCallback
import com.projects.whattowear.model.Interval

class HomePresenter(private val repository: DaysRepository) : RepositoryCallback {
    lateinit var homeView: HomeView

    override fun onGetIntervals(intervals: List<Interval>) {
        homeView.getIntervals(intervals)
    }

    override fun onError(message: String) {
        homeView.getErrorMessage(message)
    }

    fun initView() {
        repository.getIntervals(this)
    }

    fun clearCompositeDisposable() {
        repository.clearCompositeDisposable()
    }

}