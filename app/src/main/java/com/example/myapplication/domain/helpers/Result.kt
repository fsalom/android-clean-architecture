package com.example.myapplication.domain.helpers

import com.example.myapplication.data.helpers.ResultError

inline fun <T> resultOf(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (error: ResultError) {
        Result.failure(error)
    }
}