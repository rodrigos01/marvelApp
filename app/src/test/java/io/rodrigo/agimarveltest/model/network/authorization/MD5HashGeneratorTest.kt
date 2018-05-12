package io.rodrigo.agimarveltest.model.network.authorization

import org.junit.Assert.assertEquals
import org.junit.Test

class MD5HashGeneratorTest {

    @Test
    fun shouldGenerateMd5() {
        val input = "15260997536442e3fa0ad8250ceb96b82b10eec08a5f94162db9975e79a4beb64a1a1c411439f06457bd7"
        val expected = "064e7ecc53c9f1a3c051c6d11bf8601b"

        val generator = MD5HashGenerator()

        val output = generator.generateHash(input)

        assertEquals(expected, output)
    }
}