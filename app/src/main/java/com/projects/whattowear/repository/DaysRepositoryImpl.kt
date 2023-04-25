package com.projects.whattowear.repository

import com.projects.whattowear.model.Interval
import com.projects.whattowear.network.ApiCallback
import com.projects.whattowear.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toFlowable
import io.reactivex.rxjava3.schedulers.Schedulers

class DaysRepositoryImpl() : DaysRepository {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    private val apiClient by lazy {
        ApiClient()
    }

    override fun getIntervals(repositoryCallback: RepositoryCallback) {
        apiClient.getIntervalsFromApi(object : ApiCallback {
            override fun onSuccess(intervals: List<Interval>) {
                intervals.toFlowable()
                    .collect({ ArrayList() }, ArrayList<Interval>::add)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onSuccess = repositoryCallback::onGetIntervals,
                        onError = { throwable ->
                            repositoryCallback.onError(
                                throwable.message ?: "Unknown Error"
                            )
                        }
                    )
                    .addTo(compositeDisposable)
            }

            override fun onError(message: String) {
                repositoryCallback.onError(message)
            }
        })
    }


}