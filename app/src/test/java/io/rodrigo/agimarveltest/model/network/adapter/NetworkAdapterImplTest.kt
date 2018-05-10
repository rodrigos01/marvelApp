package io.rodrigo.agimarveltest.model.network.adapter

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.authorization.AuthorizationProvider
import io.rodrigo.agimarveltest.model.network.response.ApiResponse
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkAdapterImplTest {

    private val authorizationProvider = mock<AuthorizationProvider>()
    private val marvelAPI = mock<MarvelAPI>()

    @Before
    fun setup() {
        whenever(authorizationProvider.getAuthorizationData())
                .thenReturn(AuthorizationProvider.AuthorizationData(
                        "PUBKEY",
                        392,
                        "supersecrethash"
                ))
        whenever(marvelAPI.getCharacters(any(), any(), any(), any(), any()))
                .thenReturn(Single.just(ApiResponse(CharactersResponse(0, emptyList()))))
    }

    @Test
    fun shouldCallGetCharacters() {
        val adapter = getAdapter()
        adapter.getCharacters()

        verify(marvelAPI).getCharacters(any(), any(), any(), eq(30), eq(0))
    }

    @Test
    fun shouldCallGetCharactersWithLimit() {
        val adapter = getAdapter()
        adapter.getCharacters(limit = 10)

        verify(marvelAPI).getCharacters(any(), any(), any(), eq(10), eq(0))
    }

    @Test
    fun shouldCallGetCharactersWithOffset() {
        val adapter = getAdapter()
        adapter.getCharacters(offset = 30)

        verify(marvelAPI).getCharacters(any(), any(), any(), eq(30), eq(30))
    }

    @Test
    fun shouldUseAuthorizationDataFromProvider() {
        val adapter = getAdapter()
        adapter.getCharacters()

        verify(authorizationProvider).getAuthorizationData()
        verify(marvelAPI).getCharacters(
                eq("PUBKEY"),
                eq(392),
                eq("supersecrethash"),
                eq(30),
                eq(0))
    }

    private fun getAdapter(): NetworkAdapterImpl {
        return NetworkAdapterImpl(authorizationProvider, marvelAPI)
    }
}