package es.rudo.androidbaseproject.data.helpers

import es.rudo.androidbaseproject.helpers.Constants.SERVER_BADREQUEST_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_CONFLICT_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_FORBIDDEN_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_NOCONTENT_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_NOTACCEPTABLE_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_NOTFOUND_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_TIMEOUT_CODE
import es.rudo.androidbaseproject.helpers.Constants.SERVER_UNAUTHORIZED_CODE
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiHandler(call: suspend () -> Response<T>): T {
    try {
        val response = call()
        response.body()?.let { body ->
            if (response.isSuccessful) {
                return body
            } else {
                throw response.code().toHttpError()
            }
        } ?: throw RetrofitError.EmptyBody

    } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val body = e.response()?.errorBody().toString()
                    throw RetrofitError.HttpException(body)
                }
                is SocketTimeoutException -> throw RetrofitError.Timeout("Timeout Error")
                is IOException -> throw RetrofitError.Network("Thread Error")
                is ResultError -> throw e
                else -> throw RetrofitError.Unknown("Unknown Error")
            }
    }
}

fun Int.toHttpError() =
    when (this) {
        SERVER_NOCONTENT_CODE -> HttpCodeError.ServerNoContent
        SERVER_BADREQUEST_CODE -> HttpCodeError.BadRequest
        SERVER_UNAUTHORIZED_CODE -> HttpCodeError.Unauthorized
        SERVER_FORBIDDEN_CODE -> HttpCodeError.Forbidden
        SERVER_NOTFOUND_CODE -> HttpCodeError.NotFound
        SERVER_NOTACCEPTABLE_CODE -> HttpCodeError.NotAcceptable
        SERVER_TIMEOUT_CODE -> HttpCodeError.Timeout
        SERVER_CONFLICT_CODE -> HttpCodeError.ServerConflict
        else -> HttpCodeError.InternalServerError
    }
