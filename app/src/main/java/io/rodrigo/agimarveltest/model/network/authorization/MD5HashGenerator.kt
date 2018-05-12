package io.rodrigo.agimarveltest.model.network.authorization

import java.math.BigInteger

class MD5HashGenerator : HashGenerator {
    override fun generateHash(input: String): String {
        val digest = java.security.MessageDigest
                .getInstance("MD5")
        digest.update(input.toByteArray(), 0, input.length)
        val messageDigest = digest.digest()
        val result = BigInteger(1, messageDigest).toString(16)
        return if (result.length == 31) {
            "0$result"
        } else {
            result
        }
    }
}