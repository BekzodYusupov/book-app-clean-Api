package uz.gita.bookapi.readSmsCode

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 14:50
 */

//code
/**
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Log.d("TTT", "Success")
        }
        task.addOnFailureListener {
            Log.d("TTT", it.message.toString())
        }

//        val appSignatureHelper = AppSignatureHelper(this)
//        Log.d("TTT", appSignatureHelper.appSignatures[0])

        val broadcast = MySMSBroadcastReceiver()
        broadcast.setSmsCodeListener {
            findViewById<TextView>(R.id.text).text = it
        }
        val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(broadcast, filter)

//        val intent = Intent(this, MySMSBroadcastReceiver::class.java)
//        intent.action = SmsRetriever.SMS_RETRIEVED_ACTION
//        sendBroadcast(intent)

    }
}
* */