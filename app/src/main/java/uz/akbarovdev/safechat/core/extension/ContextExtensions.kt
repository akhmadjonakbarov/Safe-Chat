package uz.akbarovdev.safechat.core.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import uz.akbarovdev.safechat.core.database.SharedPreferences
import java.util.Locale


fun Context.sharedPreferences(name: String, defaultValue: String = "") =
    SharedPreferences(this, name, defaultValue)

