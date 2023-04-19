package com.projects.whattowear.local

import android.content.Context
import android.content.SharedPreferences

class PrefsUtil {
    companion object {
        private var sharedPrefs: SharedPreferences? = null
        private const val SHARED_PREFS_NAME = "MySharedPrefs"
        private const val START_TIME_AND_IMAGE_ID = "StartTimeAndImageId"

        fun initPrefs(context: Context) {
            sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        }

        var startTimeAndImageId: String?
            get() = sharedPrefs?.getString(START_TIME_AND_IMAGE_ID, null)
            set(value) {
                sharedPrefs?.edit()?.putString(START_TIME_AND_IMAGE_ID, value)?.apply()
            }
    }
}

