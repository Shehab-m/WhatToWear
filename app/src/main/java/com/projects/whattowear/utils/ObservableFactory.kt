package com.projects.whattowear.utils

import com.projects.whattowear.model.Interval
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ObservableFactory {

    fun createObservable(
        intervals: List<Interval>,
    ): Observable<List<Interval>> {

        return intervals.toObservable()
            .zipWith(
                Observable.interval(0, 3, TimeUnit.SECONDS)
            ) { interval, _ -> interval }
            .scan(emptyList<Interval>()) { acc, interval -> acc + interval }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}