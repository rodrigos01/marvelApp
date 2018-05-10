package io.rodrigo.agimarveltest.model.network.authorization

import javax.inject.Inject

class AuthorizationProviderImpl @Inject constructor(
        private val publicKey: String,
        private val privateKey: String,
        private val hashGenerator: HashGenerator,
        private val timestampGenerator: TimestampGenerator
) : AuthorizationProvider {

    override fun getAuthorizationData(): AuthorizationProvider.AuthorizationData {
        val timestamp = timestampGenerator.getTimestamp()
        val hash = hashGenerator.generateHash("$timestamp$privateKey$publicKey")
        return AuthorizationProvider.AuthorizationData(
                publicKey,
                timestamp,
                hash
        )
    }
}