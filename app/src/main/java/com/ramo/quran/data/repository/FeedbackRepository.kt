package com.ramo.quran.data.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.ramo.quran.AppConstants
import com.ramo.quran.model.Feedback
import java.util.*

class FeedbackRepository {
    fun saveFeedback(feedback: Feedback) {
        val ref = Firebase.database.getReference(AppConstants.FIREBASE_FEEDBACK_REF)
        val key = ref.push().key ?: UUID.randomUUID().toString()
        ref.child(key).setValue(feedback)
    }
}