package es.rudo.androidbaseproject.domain.usecase

import es.rudo.androidbaseproject.data.helpers.RetrofitError
import es.rudo.androidbaseproject.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.domain.helpers.resultOf
import es.rudo.androidbaseproject.helpers.Constants.RETRY_TIME_IN_MILLIS
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