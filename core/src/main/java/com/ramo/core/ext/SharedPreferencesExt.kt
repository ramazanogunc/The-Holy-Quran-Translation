package com.ramo.core.ext

import android.content.SharedPreferences
import com.google.gson.Gson

fun SharedPreferences.set(key: String, value: Any?) {
    val editor = this.edit()

    if (value == null) {
        editor.remove(key)
        editor.apply()
        return
    }
    when (value) {
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Float -> editor.putFloat(key, value)
        is String -> editor.putString(key, value)
        else -> editor.putString(key, Gson().toJson(value))
    }
    editor.apply()
}

inline fun <reified T> SharedPreferences.get(key: String): T? {

    if (!this.contains(key)) {
        return null
    }

    return when (T::class) {
        Boolean::class -> this.getBoolean(key, false) as T
        Float::class -> this.getFloat(key, 0f) as T
        Int::class -> this.getInt(key, 0) as T
        Long::class -> this.getLong(key, 0) as T
        String::class -> this.getString(key, null) as T
        else -> {
            Gson().fromJson(getString(key, ""), T::class.java)
        }
    }
}