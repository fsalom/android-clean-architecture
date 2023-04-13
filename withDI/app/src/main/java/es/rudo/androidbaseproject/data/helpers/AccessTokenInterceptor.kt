package es.rudo.androidbaseproject.data.helpers

import es.rudo.androidbaseproject.data.source.local.preferences.AppPreferences
import es.rudo.androidbaseproject.data.helpers.Config.HTTP_CLIENT_AUTHORIZATION
import es.rudo.androidbaseproject.data.helpers.Config.TYPE_ITEM_AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val preferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preferences.accessToken
        val request = newRequestWithAccessToken(chain.request(), accessToken.toString())
        return chain.proceed(request)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header(TYPE_ITEM_AUTHORIZATION, "$HTTP_CLIENT_AUTHORIZATION $accessToken")
            .build()
    }
}
