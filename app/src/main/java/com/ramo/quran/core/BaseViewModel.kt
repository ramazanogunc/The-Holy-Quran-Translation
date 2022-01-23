package com.ramo.quran.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun <T : Any?> exec(
        showLoading: Boolean = true,
        request: suspend () -> T,
        success: ((T) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null,
    ): Job {
        return viewModelScope.launch {
            if (showLoading) {
                showLoading()
            }

            try {
                val result = request.invoke()
                success?.invoke(result)
            } catch (exception: Exception) {
                if (error != null) error.invoke(exception)
                else handleException(exception)

            }

            if (showLoading) {
                hideLoading()
            }
        }
    }

    private fun handleException(exception: Exception) {}

    private fun showLoading() =  _loading.postValue(true)

    private fun hideLoading() =  _loading.postValue(false)
}