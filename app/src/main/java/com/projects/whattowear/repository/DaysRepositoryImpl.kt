package com.projects.whattowear.repository

import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiCallback
import com.projects.whattowear.network.ApiService
import com.projects.whattowear.utils.ObservableFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

class DaysRepositoryImpl(private val apiClient: ApiService) : DaysRepository {
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

    override fun clearCompositeDisposable() {
        compositeDisposable.dispose()
    }

    private val observable = ObservableFactory()

    private fun onRepositoryCallbackGetIntervals(
        intervals: List<Interval>,
        repositoryCallback: RepositoryCallback,
        compositeDisposable: CompositeDisposable
    ) {
        observable.createObservable(intervals)
            .subscribeBy(
                onNext = { intervalList ->
                    repositoryCallback.onGetIntervals(intervalList)
                },
                onError = { throwable ->
                    repositoryCallback.onError(
                        throwable.message ?: "Unknown Error"
                    )
                }
            )
            .addTo(compositeDisposable)
    }



}

