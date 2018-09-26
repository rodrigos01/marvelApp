package io.rodrigo.agimarveltest.ui

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val pageSize: Int,
        val status: LiveData<Status>,
        val refresh: () -> Unit
) {
    enum class Status {
        STATUS_LOADING, STATUS_ERROR, STATUS_INITIALIZED
    }
}