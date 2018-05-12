package io.rodrigo.agimarveltest.model.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.rodrigo.agimarveltest.model.Promise
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter
import io.rodrigo.agimarveltest.model.network.response.ItemListResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarvelComicRepositoryTest {

    private val networkAdapter = mock<NetworkAdapter>()

    @Before
    fun setup() {
        whenever(networkAdapter.getComics(any()))
                .thenReturn(Promise.just(ItemListResponse(0, emptyList())))
    }

    @Test
    fun shouldCallAdapterComics() {
        val repository = MarvelComicRepository(networkAdapter)

        repository.getComics(700)

        verify(networkAdapter).getComics(700)
    }

}