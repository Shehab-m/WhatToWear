package com.projects.whattowear.network

import com.projects.whattowear.model.Interval
import io.reactivex.rxjava3.core.Single

interface ApiService {
    fun getIntervalsFromApi(): Single<List<Interval>>
}