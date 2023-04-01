package es.rudo.androidbaseproject.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    open fun initData(data: Bundle) { }

    fun <T> background(callback: ((T?) -> Unit)? = null, process: () -> T?) = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) {
            process()
        }

        callback?.let {
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }
}