package es.rudo.androidbaseproject.data.source.local.preferences

import android.content.SharedPreferences
import es.rudo.androidbaseproject.data.model.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    val sharedPreferences: SharedPreferences?
) {

    var accessToken: String? by preferences()

    var refreshToken: String? by preferences()

    fun clear() {
        sharedPreferences?.edit()?.clear()?.apply()
    }
}
