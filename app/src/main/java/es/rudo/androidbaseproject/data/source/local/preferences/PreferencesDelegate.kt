package es.rudo.androidbaseproject.data.source.local.preferences

import com.google.gson.Gson
import kotlin.reflect.KProperty

class PreferencesDelegate(val key: String? = null) {

    inline operator fun <reified T> getValue(thisRef: AppPreferences, property: KProperty<*>): T? {
        val json = thisRef.sharedPreferences?.getString(key ?: "KEY_${property.name.uppercase()}", "")
        return json?.let {
            if (it.isNotEmpty()) {
                if (it.contains(";;")) {
                    val stringList = it.split(";;")
                    val type = Class.forName(stringList.last())
                    stringList.dropLast(1).map { element ->
                        Gson().fromJson(element, type)
                    } as T
                } else {
                    Gson().fromJson(it, T::class.java)
                }
            } else {
                null
            }
        }
    }

    inline operator fun <reified T> setValue(thisRef: AppPreferences, property: KProperty<*>, value: T?) {
        value?.let {
            val json =
                if (it is Iterable<*>) {
                    if (!it.none()) {
                        it.joinToString(separator = ";;") { element ->
                            Gson().toJson(element, element?.javaClass)
                        } + ";;${it.first()?.javaClass?.name}"
                    } else ""
                } else {
                    Gson().toJson(it, T::class.java)
                }

            thisRef.sharedPreferences?.edit()?.putString(
                key ?: "KEY_${property.name.uppercase()}",
                json
            )?.apply()
        } ?: kotlin.run {
            thisRef.sharedPreferences?.edit()?.remove(
                key ?: "KEY_${property.name.uppercase()}"
            )?.apply()
        }
    }
}

fun preferences(key: String? = null) = PreferencesDelegate(key)
