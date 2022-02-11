package com.ramo.quran.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    fun <T : Any?> exec(
        request: suspend () -> T,
        success: ((T) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null,
    ): Job {
        return viewModelScope.launch {
            try {
                val result = request.invoke()
                success?.invoke(result)
            } catch (exception: Exception) {
                if (error != null) error.invoke(exception)
                else handleException(exception)

            }
        }
    }

    private fun handleException(exception: Exception) {
        if (!exception.localizedMessage.isNullOrBlank())
            _toast.value = exception.localizedMessage
    }
}