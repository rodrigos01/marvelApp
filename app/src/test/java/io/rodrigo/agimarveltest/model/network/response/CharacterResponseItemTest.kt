package io.rodrigo.agimarveltest.model.network.response

import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterResponseItemTest {

    @Test
    fun shouldConvertResponseToMarvelCharacter() {
        val responseItem = CharacterResponseItem(
                123,
                "bugman",
                null,
                "A Bug catcher super-hero",
                Thumbnail(
                        "https://s3-media2.fl.yelpcdn.com/bphoto/IAITw8luZ0Q77qw7EpHniw/ls",
                        "jpg"
                )
        )
        val character = responseItem.toMarvelCharacter()

        assertEquals(123, character.id)
        assertEquals("bugman", character.name)
        assertEquals("A Bug catcher super-hero", character.description)
        assertEquals("https://s3-media2.fl.yelpcdn.com/bphoto/IAITw8luZ0Q77qw7EpHniw/ls.jpg", character.imageUrl)
    }
}