package com.projects.whattowear.fragment

import com.projects.whattowear.model.Interval

interface HomeView {
    fun getIntervals(intervals: List<Interval>)
    fun getErrorMessage(message: String)
}