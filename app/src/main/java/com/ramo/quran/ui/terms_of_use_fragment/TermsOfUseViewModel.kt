package com.ramo.quran.ui.terms_of_use_fragment

import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.FeedbackRepository
import com.ramo.quran.model.Feedback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfUseViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) : BaseViewModel() {

    fun sendFeedBack(description: String) {
        feedbackRepository.saveFeedback(Feedback(description = description.trim()))
    }
}