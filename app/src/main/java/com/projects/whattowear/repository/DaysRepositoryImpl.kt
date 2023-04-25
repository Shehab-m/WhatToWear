package com.projects.whattowear.repository

import android.util.Log
import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiCallback
import com.projects.whattowear.network.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toFlowable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class DaysRepositoryImpl(private val apiClient: ApiService) : DaysRepository {
    private val observer = Observer()
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun getIntervals(repositoryCallback: RepositoryCallback) {
        apiClient.getIntervalsFromApi(object : ApiCallback {
            override fun onSuccess(intervals: List<Interval>) {
                onRepositoryCallbackGetIntervals(intervals, repositoryCallback, compositeDisposable)
            }

            override fun onError(message: String) {
                repositoryCallback.onError(message)
            }
        })
    }

    private fun onRepositoryCallbackGetIntervals(
        intervals: List<Interval>,
        repositoryCallback: RepositoryCallback,
        compositeDisposable: CompositeDisposable
    ) {
        observer.createObserver(intervals)
            .subscribeBy(
                onNext = { intervals ->
                    Log.i("intervals", "${intervals}: ")
                    repositoryCallback.onGetIntervals(intervals)
                },
                onError = { throwable ->
                    Log.i("intervals", "${throwable}656256565642: ")
                    repositoryCallback.onError(
                        throwable.message ?: "Unknown Error"
                    )
                }
            )
            .addTo(compositeDisposable)
    }

//الطريقة العادية
//    private fun onRepositoryCallbackGetIntervals(
//        intervals: List<Interval>,
//        repositoryCallback: RepositoryCallback,
//        compositeDisposable: CompositeDisposable
//    ) {
//        intervals.toFlowable()
//            .collect({ ArrayList() }, ArrayList<Interval>::add)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = repositoryCallback::onGetIntervals,
//                onError = { throwable ->
//                    repositoryCallback.onError(
//                        throwable.message ?: "Unknown Error"
//                    )
//                }
//            )
//            .addTo(compositeDisposable)
//    }

    override fun clearCompositeDisposable() {
        compositeDisposable.dispose()
    }


// طريقة باظت
//    private fun onRepositoryCallbackGetIntervals(
//        intervals: List<Interval>,
//        repositoryCallback: RepositoryCallback,
//        compositeDisposable: CompositeDisposable
//    ) {
//        Observable.interval(0, 3, TimeUnit.SECONDS)
//            .take(intervals.size.toLong())
//            .flatMap { index ->
//                Observable.just(intervals[index.toInt()])
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
///            .toList()
//            .subscribeBy(
//                onSuccess = { intervalList ->
//                    repositoryCallback.onGetIntervals(intervalList)
//                },
//                onError = { throwable ->
//                    repositoryCallback.onError(
//                        throwable.message ?: "Unknown Error"
//                    )
//                }
//            )
//            .addTo(compositeDisposable)
//    }


//    private fun onRepositoryCallbackGetIntervals(
//        intervals: List<Interval>,
//        repositoryCallback: RepositoryCallback,
//        compositeDisposable: CompositeDisposable
//    ) {
//        val intervalSubject = BehaviorSubject.create<Interval>()
//
//        Observable.interval(0, 3, TimeUnit.SECONDS)
//            .zipWith(
//                intervals.toObservable()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//            ) { _, interval -> interval }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { interval ->
//                    intervalSubject.onNext(interval)
//                },
//                { throwable ->
//                    repositoryCallback.onError(
//                        throwable.message ?: "Unknown Error"
//                    )
//                },
//                {
//                    repositoryCallback.onGetIntervals(intervals)
//                }
//            )
//            .addTo(compositeDisposable)
//
//        intervalSubject
//            .observeOn(AndroidSchedulers.mainThread())
//            .scan(emptyList<Interval>()) { acc, interval -> acc + interval }
//            .subscribeBy(
//                onNext = { intervals ->
//                    repositoryCallback.onGetIntervals(intervals)
//                },
//                onError = { throwable ->
//                    repositoryCallback.onError(
//                        throwable.message ?: "Unknown Error"
//                    )
//                }
//            )
//            .addTo(compositeDisposable)
//    }


//    val observable = Observable.fromCallable {
//
//    }


//    private fun repositoryCallbackGetIntervals(
//        intervals: List<Interval>,
//        repositoryCallback: RepositoryCallback,
//        compositeDisposable: CompositeDisposable
//    ) {
//        //{up stream}
//        // as it's responsible fro emitting the interval objects to the down stream
//        intervals.toFlowable()
//            //    .onBackpressureBuffer()
//            .collect({ ArrayList() }, ArrayList<Interval>::add)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            //{down stream subscriber}
//            // as it receives these emissions and process them in on success and on error
//            .subscribeBy(
//                onSuccess = repositoryCallback::onGetIntervals,
//                onError = { throwable ->
//                    repositoryCallback.onError(
//                        throwable.message ?: "Unknown Error"
//                    )
//                }
//            )
//            .addTo(compositeDisposable)
//    }
//
//    override fun clearCompositeDisposable() {
//        compositeDisposable.clear()
//    }


}

