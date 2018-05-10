package io.rodrigo.agimarveltest.model.network.authorization

import org.junit.Assert.assertEquals
import org.junit.Test

class MD5HashGeneratorTest {

    @Test
    fun shouldGenerateMd5() {
        val input = "392PRIVKEYPUBKEY"
        val expected = "46f2680432bd2e9817c063cb9e4a77af"

        val generator = MD5HashGenerator()

        val output = generator.generateHash(input)

        assertEquals(expected, output)
    }
}