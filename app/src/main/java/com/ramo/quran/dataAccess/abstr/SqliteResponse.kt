package com.ramo.quran.dataAccess.abstr

interface SqliteResponse<T> {
    fun onSuccess(response :T)
    fun onFail(failMessage: String)
}