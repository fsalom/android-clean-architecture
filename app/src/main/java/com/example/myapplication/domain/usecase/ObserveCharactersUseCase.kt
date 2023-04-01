package com.example.myapplication.domain.usecase

import com.example.myapplication.data.helpers.RetrofitError
import com.example.myapplication.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.myapplication.data.model.Character
import com.example.myapplication.domain.helpers.resultOf
import com.example.myapplication.helpers.Constants.RETRY_TIME_IN_MILLIS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen

class ObserveCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke() : Flow<Result<List<Character>>> =
        characterRepository
            .observeCharacters()
            .map {
                resultOf { it }
            }
            .retryWhen { cause, attempt ->
                if (cause is RetrofitError.Network && attempt < 3) {
                    emit(Result.failure(cause))

                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }
            .catch {
                emit(Result.failure(it))
            }
}