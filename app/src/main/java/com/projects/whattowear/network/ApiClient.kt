package com.projects.whattowear.network

import com.projects.whattowear.BuildConfig
import com.projects.whattowear.model.Interval
import com.projects.whattowear.utils.Constants.Companion.BASE_URL
import com.projects.whattowear.utils.Constants.Companion.FIELDS
import com.projects.whattowear.utils.Constants.Companion.LOCATION
import com.projects.whattowear.utils.Constants.Companion.SCHEME
import com.projects.whattowear.utils.Constants.Companion.TIME_STEPS
import com.projects.whattowear.utils.Constants.Companion.TIME_ZONE
import io.reactivex.rxjava3.core.Single
import okhttp3.*
import java.io.IOException

class ApiClient(private val client: OkHttpClient): ApiService {
    private val utils = NetworkUtils()

    private val httpUrl =
        HttpUrl.Builder().scheme(SCHEME).host(BASE_URL).addPathSegments("v4")
            .addPathSegment("timelines").addQueryParameter("apikey", BuildConfig.apikey)
            .addQueryParameter("fields", FIELDS)
            .addQueryParameter("location", LOCATION)
            .addQueryParameter("timesteps", TIME_STEPS)
            .addQueryParameter("timezone", TIME_ZONE).build()

    override fun getIntervalsFromApi(): Single<List<Interval>> {
        val request = Request.Builder().url(httpUrl).build()
        return Single.create { emitter ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(e)
                }
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val jsonString = response.body?.string()
                        val jsonArray = utils.getIntervalsJsonArrayFromJson(jsonString!!)
                        val intervals = utils.parseIntervals(jsonArray)
                        emitter.onSuccess(intervals)
                    } catch (e: Exception) {
                        emitter.onError(e)
                    } finally {
                        response.close()
                    }
                }
            })
        }
    }
}


//package com.projects.whattowear.network
//
//import com.projects.whattowear.BuildConfig
//import com.projects.whattowear.utils.Constants.Companion.BASE_URL
//import com.projects.whattowear.utils.Constants.Companion.FIELDS
//import com.projects.whattowear.utils.Constants.Companion.LOCATION
//import com.projects.whattowear.utils.Constants.Companion.SCHEME
//import com.projects.whattowear.utils.Constants.Companion.TIME_STEPS
//import com.projects.whattowear.utils.Constants.Companion.TIME_ZONE
//import okhttp3.*
//import okhttp3.logging.HttpLoggingInterceptor
//import java.io.IOException
//
//class com.projects.whattowear.network.ApiClient(): ApiService {
//    private val utils = NetworkUtils()
//
//    private val client: OkHttpClient by lazy {
//        val interceptor =
//            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
//        OkHttpClient.Builder().addInterceptor(interceptor).build()
//    }
//
//    private val httpUrl =
//        HttpUrl.Builder().scheme(SCHEME).host(BASE_URL).addPathSegments("v4")
//            .addPathSegment("timelines").addQueryParameter("apikey", BuildConfig.apikey)
//            .addQueryParameter("fields", FIELDS)
//            .addQueryParameter("location", LOCATION)
//            .addQueryParameter("timesteps", TIME_STEPS)
//            .addQueryParameter("timezone", TIME_ZONE).build()
//
//
//
////    override fun getIntervalsFromApi(apiCallback: ApiCallback) {
////        val request = Request.Builder().url(httpUrl).build()
////        client.newCall(request).enqueue(object : Callback {
////
////            override fun onFailure(call: Call, e: IOException) {
////                e.message?.let { apiCallback.onError(it) }
////            }
////
////            override fun onResponse(call: Call, response: Response) {
////                response.body?.string().let { jsonString ->
////                    val jsonArray = utils.getIntervalsJsonArrayFromJson(jsonString!!)
////                    val intervals = utils.parseIntervals(jsonArray)
////                    apiCallback.onSuccess(intervals)
////                }
////            }
////        })
////    }
//
//
//
//}