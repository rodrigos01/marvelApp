package io.rodrigo.agimarveltest.model.hash

import org.junit.Test

import org.junit.Assert.*

class HashFunctionsTest {

    @Test
    fun shouldGenerateMd5() {
        val input = "my nice input"
        val expected = "12fa084e4ea313c148a1f54f0cb7b5ee"
        val output = generateMd5(input)

        assertEquals(expected, output)
    }
}