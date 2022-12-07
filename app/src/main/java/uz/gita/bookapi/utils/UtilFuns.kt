package uz.gita.bookapi.utils

import android.util.Log
import uz.gita.bookapi.BuildConfig

fun mLog(message: String, tag: String = "ZZZ") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message)
//        PlutoLog.d(tag, message)
    }
}