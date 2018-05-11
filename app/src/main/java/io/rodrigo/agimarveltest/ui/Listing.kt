package io.rodrigo.agimarveltest.ui

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val pageSize: Int,
        val status: LiveData<Status>
) {
    enum class Status {
        STATUS_LOADING, STATUS_ERROR, STATUS_INITIALIZED
    }
}