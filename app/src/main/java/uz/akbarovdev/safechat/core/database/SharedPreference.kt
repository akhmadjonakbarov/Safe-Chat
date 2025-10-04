package uz.akbarovdev.safechat.core.database

import android.content.Context
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferences(
    private val context: Context,
    private val name: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Any?, String> {
    private val sharedPreferences by lazy {
        context.getSharedPreferences("safe_chat_pref", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreferences.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        sharedPreferences.edit { putString(name, value) }
    }

}



