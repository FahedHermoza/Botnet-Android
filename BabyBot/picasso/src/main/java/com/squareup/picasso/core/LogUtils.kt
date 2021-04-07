package com.squareup.picasso.core

import android.util.Log

/**
 *
 */
object LogUtils {
    private const val TAG = "CONSOLE"
    private var isDebug = false

    fun setup(debug: Boolean) {
        isDebug = debug
    }

    fun v(message: String) {
        if (isDebug) {
            Log.v(TAG, message)
        }
    }

    fun i(message: String) {
        if (isDebug) {
            Log.i(TAG, message)
        }
    }

    fun d(message: String) {
        if (isDebug) {
            Log.i(TAG, message)
        }
    }

    fun e(message: String) {
        if (isDebug) {
            Log.e(TAG, message)
        }
    }


}