package io.rodrigo.agimarveltest.model.network.authorization

interface HashGenerator {
    fun generateHash(input: String): String
}