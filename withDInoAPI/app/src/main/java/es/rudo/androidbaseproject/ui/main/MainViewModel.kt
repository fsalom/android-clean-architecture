package es.rudo.androidbaseproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.rudo.androidbaseproject.data.model.Character
import es.rudo.androidbaseproject.domain.usecase.CharactersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    fun loadCharacters() = viewModelScope.launch {
        charactersUseCase.getCharacters().also {
            _characters.postValue(it)
        }
    }
}