package io.rodrigo.agimarveltest.model.network.authorization

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthorizationProviderImplTest {

    private val publicKey = "PUBKEY"
    private val privateKey = "PRIVKEY"

    private val timestampGenerator = mock<TimestampGenerator>()
    private val hashGenerator = mock<HashGenerator>()

    @Before
    fun setup() {
        whenever(timestampGenerator.getTimestamp()).thenReturn(392)
        whenever(hashGenerator.generateHash(any())).thenReturn("supersecrethash")
    }

    @Test
    fun shouldGenerateTimestampWithGenerator() {
        val provider = getProvider()
        provider.getAuthorizationData()

        verify(timestampGenerator).getTimestamp()
    }

    @Test
    fun shouldUseTimestampFromGenerator() {
        val provider = getProvider()
        val authData = provider.getAuthorizationData()

        Assert.assertEquals(authData.timestamp, 392)
    }

    @Test
    fun shouldGenerateHashWithGenerator() {
        val provider = getProvider()
        provider.getAuthorizationData()

        val expectedInput = "392PRIVKEYPUBKEY"

        verify(hashGenerator).generateHash(expectedInput)
    }

    @Test
    fun shouldUseHashFromGenerator() {
        val provider = getProvider()
        val authData = provider.getAuthorizationData()

        Assert.assertEquals(authData.hash, "supersecrethash")
    }

    @Test
    fun shouldUsePublicKey() {
        val provider = getProvider()
        val authData = provider.getAuthorizationData()

        Assert.assertEquals(authData.apiKey, "PUBKEY")
    }

    fun getProvider() = AuthorizationProviderImpl(publicKey, privateKey, hashGenerator, timestampGenerator)

}