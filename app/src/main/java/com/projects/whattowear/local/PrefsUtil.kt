package com.projects.whattowear.local

import android.content.Context
import android.content.SharedPreferences

class PrefsUtil {
    companion object {
        private var sharedPrefs: SharedPreferences? = null
        private const val SHARED_PREFS_NAME = "MySharedPrefs"
        private const val KEY_TODAY_START_TIME = "KeyTodayStartTime"
        private const val KEY_INTERVALS_IMAGE_ID_LIST = "KeyIntervalsImageIdList"
        private const val START_TIME_AND_IMAGE_ID = "StartTimeAndImageId"

        fun initPrefs(context: Context) {
            sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        }

        var todayStartTime: String?
            get() = sharedPrefs?.getString(KEY_TODAY_START_TIME, null)
            set(value) {
                sharedPrefs?.edit()?.putString(KEY_TODAY_START_TIME, value)?.apply()
            }

        var intervalsImageIdList: String?
            get() = sharedPrefs?.getString(KEY_INTERVALS_IMAGE_ID_LIST, null)
            set(value) {
                sharedPrefs?.edit()?.putString(KEY_INTERVALS_IMAGE_ID_LIST, value)?.apply()
            }

        var startTimeAndImageId: String?
            get() = sharedPrefs?.getString(START_TIME_AND_IMAGE_ID, null)
            set(value) {
                sharedPrefs?.edit()?.putString(START_TIME_AND_IMAGE_ID, value)?.apply()
            }
    }
}


//package com.projects.whattowear.local
//
//import android.content.Context
//import android.content.SharedPreferences
//
//class MyPreferences(context: Context) {
//    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
//
//    companion object {
//        private const val SHARED_PREFS_NAME = "MySharedPrefs"
//        private const val KEY_TODAY_START_TIME = "KeyTodayStartTime"
//        private const val KEY_INTERVALS_IMAGE_ID_LIST = "KeyIntervalsImageIdList"
//        private const val START_TIME_AND_IMAGE_ID = "StartTimeAndImageId"
//
//        fun get(context: Context): MyPreferences {
//            return MyPreferences(context)
//        }
//    }
//
//    var todayStartTime: String?
//        get() = sharedPrefs.getString(KEY_TODAY_START_TIME, null)
//        set(value) = sharedPrefs.edit().putString(KEY_TODAY_START_TIME, value).apply()
//
//    var intervalsImageIdList: String?
//        get() = sharedPrefs.getString(KEY_INTERVALS_IMAGE_ID_LIST, null)
//        set(value) = sharedPrefs.edit().putString(KEY_INTERVALS_IMAGE_ID_LIST, value).apply()
//
//    var startTimeAndImageId: String?
//        get() = sharedPrefs.getString(START_TIME_AND_IMAGE_ID, null)
//        set(value) = sharedPrefs.edit().putString(START_TIME_AND_IMAGE_ID, value).apply()
//}
