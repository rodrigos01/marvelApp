package io.rodrigo.agimarveltest.model.network.authorization

interface AuthorizationProvider {

    fun getAuthorizationData(): AuthorizationData

    data class AuthorizationData(
            val apiKey: String,
            val timestamp: Long,
            val hash: String
    )
}