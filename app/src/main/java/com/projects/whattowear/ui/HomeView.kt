package com.projects.whattowear.ui

import com.projects.whattowear.model.Interval

interface HomeView {
    fun getIntervals(intervals: List<Interval>)
    fun getErrorMessage(message: String)
}