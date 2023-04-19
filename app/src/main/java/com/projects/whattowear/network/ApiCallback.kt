package com.projects.whattowear.network

import com.projects.whattowear.model.Interval

interface ApiCallback {
    fun onSuccess(intervals: List<Interval>)
    fun onError(message: String)
}