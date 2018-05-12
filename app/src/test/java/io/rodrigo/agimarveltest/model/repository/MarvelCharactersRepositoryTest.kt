package io.rodrigo.agimarveltest.model.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.PositionalDataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter
import io.rodrigo.agimarveltest.model.network.response.ItemListResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarvelCharactersRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val networkAdapter = mock<NetworkAdapter>()

    @Before
    fun setup() {
        whenever(networkAdapter.getCharacters(any(), any()))
                .thenReturn(Single.just(ItemListResponse(1000, emptyList())))
    }

    @Test
    fun shouldUseLoadSizeOnInitialLoad() {
        val dataSource = createDataSource()

        val params = PositionalDataSource.LoadInitialParams(
                0,
                30,
                30,
                true
        )
        dataSource.loadInitial(params, mock())

        verify(networkAdapter).getCharacters(30)
    }

    @Test
    fun shouldUseLoadSizeAndStartPositionOnRangeLoad() {
        val dataSource = createDataSource()

        val params = PositionalDataSource.LoadRangeParams(
                22,
                54
        )
        dataSource.loadRange(params, mock())

        verify(networkAdapter).getCharacters(54, 22)
    }

    private fun createRepository() = MarvelCharactersRepository(networkAdapter)
    private fun createDataSource() = createRepository().factory.create() as MarvelCharactersRepository.CharactersDataSource
}