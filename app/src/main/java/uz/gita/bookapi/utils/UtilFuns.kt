package uz.gita.bookapi.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.viewbinding.ViewBinding
import uz.gita.bookapi.BuildConfig

fun mLog(message: String, tag: String = "ZZZ") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message)
//        PlutoLog.d(tag, message)
    }
}

fun Dialog.config(viewBinding: ViewBinding) {
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(viewBinding.root)
}