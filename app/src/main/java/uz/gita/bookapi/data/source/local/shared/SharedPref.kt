package uz.gita.bookapi.data.source.local.shared

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Local_shp", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    var token: String
        get() = sharedPreferences.getString("TOKEN", "")!!
        set(value) = editor.putString("TOKEN", value).apply()

    var signedIn: Boolean
        get() = sharedPreferences.getBoolean("SIGNED_IN", false)
        set(value) = editor.putBoolean("SIGNED_IN", value).apply()
}