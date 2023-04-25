package com.projects.whattowear.repository

import android.util.Log
import com.projects.whattowear.model.Interval
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Observer {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    fun clearCompositeDisposable() {
        compositeDisposable.dispose()
    }

//    fun createObserver(
//        intervals: List<Interval>
//    ): Observable<List<Interval>> {
//        return intervals.toObservable()
//            .zipWith(
//                Observable.interval(0, 3, TimeUnit.SECONDS)
//            ) { interval, _ -> interval }
//            .scan(emptyList()) { acc, interval ->
//                Log.i("acc", "${interval}:1432134 ")
//                acc + interval }
//    }

//    fun createObserver(
//        intervalList: List<Interval>
//    ): Observable<List<Interval>> {
//        return Observable.interval(0, 3, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .zipWith(
//                intervalList.toObservable()
//            ) { _, interval -> interval }
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMapIterable { intervalList }
//            .scan(emptyList()) { acc, interval ->
//                Log.i("acc", "${interval}:1432134 ")
//                acc + interval
//            }

  //  }

    fun createObserver(intervals: List<Interval>): Observable<List<Interval>> {
        return Observable.fromIterable(intervals)
            .concatMap { interval ->
                Observable.just(interval).delay(3, TimeUnit.SECONDS)
            }
            .scan(emptyList()) { acc, interval ->
                Log.i("acc", "${interval}:1432134 ")
                acc + interval
            }
    }



}