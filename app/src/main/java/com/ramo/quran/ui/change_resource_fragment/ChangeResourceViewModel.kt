package com.ramo.quran.ui.change_resource_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.ConfigRepository
import com.ramo.quran.data.repository.ResourceRepository
import com.ramo.quran.model.db_translation.ResourceWithLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeResourceViewModel @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val configRepository: ConfigRepository
) : BaseViewModel() {

    private val _resourceList = MutableLiveData<List<ResourceWithLanguage>>()
    val resourceList: LiveData<List<ResourceWithLanguage>> = _resourceList

    private val _currentResourceId = MutableLiveData<Int>()
    val currentResourceId: LiveData<Int> = _currentResourceId

    fun getCurrentResourceId(): Int {
        return currentResourceId.value ?: -1
    }

    fun getData() {
        getResource()
        getCurrentResource()
    }

    fun updateCurrentResource(resourceId: Int) {
        exec(
            showLoading = false,
            request = { configRepository.updateCurrentResource(resourceId) },
            success = { getCurrentResource() }
        )
    }

    private fun getResource() {
        exec(
            showLoading = true,
            request = { resourceRepository.getAll() },
            success = { _resourceList.value = it }
        )
    }

    private fun getCurrentResource() {
        exec(
            showLoading = false,
            request = { configRepository.getConfig() },
            success = { _currentResourceId.value = it.currentResourceId }
        )
    }

}