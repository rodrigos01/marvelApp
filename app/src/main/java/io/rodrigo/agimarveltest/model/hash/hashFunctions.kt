package io.rodrigo.agimarveltest.model.hash

import java.math.BigInteger

fun generateMd5(input: String): String {
    val digest = java.security.MessageDigest
            .getInstance("MD5")
    digest.update(input.toByteArray(), 0, input.length)
    val messageDigest = digest.digest()
    return BigInteger(1, messageDigest).toString(16)
}