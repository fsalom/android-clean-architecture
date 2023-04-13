package es.rudo.androidbaseproject.data.helpers

import es.rudo.androidbaseproject.data.source.local.preferences.AppPreferences
import es.rudo.androidbaseproject.data.helpers.Config.HTTP_CLIENT_AUTHORIZATION
import es.rudo.androidbaseproject.data.helpers.Config.TYPE_ITEM_AUTHORIZATION
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AccessTokenAuthenticator @Inject constructor(
    private val preferences: AppPreferences,
    //private val userRepository: UserRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = preferences.accessToken
        if (!isRequestWithAccessToken(response) || accessToken == null) {
            return null
        }
        synchronized(this) {
            val newAccessToken = preferences.accessToken
            // Access token is refreshed in another thread.
            if (accessToken != newAccessToken) {
                return newRequestWithAccessToken(response.request, newAccessToken!!)
            }
            // Need to refresh an access token
            /*val call: Call<Token> = userRepository.refreshToken()
            val res = call.execute()
            val data = res.body()
            return if (data != null) {
                val updatedAccessToken = data.access_token
                val updatedRefreshToken = data.refresh_token
                preferences.accessToken = updatedAccessToken
                preferences.refreshToken = updatedRefreshToken
                newRequestWithAccessToken(response.request, updatedAccessToken)
            } else {
                newRequestWithAccessToken(response.request, preferences.accessToken!!)
            }*/
            return newRequestWithAccessToken(response.request, "")
        }
    }

    private fun isRequestWithAccessToken(response: Response): Boolean {
        val header = response.request.header(TYPE_ITEM_AUTHORIZATION)
        return header != null && header.startsWith(HTTP_CLIENT_AUTHORIZATION)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header(
                TYPE_ITEM_AUTHORIZATION,
                "$HTTP_CLIENT_AUTHORIZATION $accessToken"
            )
            .build()
    }
}