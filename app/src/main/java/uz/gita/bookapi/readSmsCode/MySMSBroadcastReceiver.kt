/*
package uz.infinity.readsmscodeappication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class MySMSBroadcastReceiver : BroadcastReceiver() {
    private var smsCodeListener: ((String) -> Unit)? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras

            extras?.get(SmsRetriever.EXTRA_STATUS)?.let {
                val status = it as Status

                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE)
                        Log.d("TTT", "message = $message")
                        smsCodeListener?.invoke(message as String)
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        Log.d("TTT", "TIMEOUT")
                    }
                    else -> {}
                }
            }
        }
    }

    fun setSmsCodeListener(block: (String) -> Unit) {
        smsCodeListener = block
    }
}



*/
