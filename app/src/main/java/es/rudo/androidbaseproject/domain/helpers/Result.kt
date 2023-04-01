package es.rudo.androidbaseproject.domain.helpers

import es.rudo.androidbaseproject.data.helpers.ResultError

inline fun <T> resultOf(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (error: ResultError) {
        Result.failure(error)
    }
}