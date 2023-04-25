package com.projects.whattowear.repository

import com.projects.whattowear.model.Interval

interface RepositoryCallback {
    fun onGetIntervals(intervals: List<Interval>)
    fun onError(message: String)
}