package io.rodrigo.agimarveltest.model.repository

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.response.ApiResponse
import io.rodrigo.agimarveltest.model.network.response.CharacterResponseItem
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse
import io.rodrigo.agimarveltest.model.network.response.Thumbnail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarvelCharactersRepositoryTest {

    private val marvelAPI = mock<MarvelAPI>()

    @Before
    fun setup() {
        val items = listOf(
                CharacterResponseItem(
                        111,
                        "Bugman",
                        "A Bug catcher super-hero",
                        Thumbnail(
                                "https://s3-media2.fl.yelpcdn.com/bphoto/IAITw8luZ0Q77qw7EpHniw/ls",
                                "jpg"
                        )
                ),
                CharacterResponseItem(
                        222,
                        "Nullpointer",
                        "Bugman's arch villain",
                        Thumbnail(
                                "https://michaelrice.com/wp-content/uploads/2016/08/nullpointerexception",
                                "jpg"
                        )
                ),
                CharacterResponseItem(
                        333,
                        "Debuger",
                        "Bugman's sidekick",
                        Thumbnail(
                                "https://d26ilriwvtzlb.cloudfront.net/d/db/DebugGame1",
                                "jpg"
                        )
                )
        )
        whenever(marvelAPI.getCharacters(any(), any(), any(), any(), any()))
                .thenReturn(Single.just(ApiResponse(CharactersResponse(items))))
    }

    @Test
    fun shouldCallGetCharacters() {
        val repository = MarvelCharactersRepository(marvelAPI)
        repository.getCharacters()

        verify(marvelAPI).getCharacters(any(), any(), any(), any(), any())
    }

    @Test
    fun shouldCallGetCharactersWithOffset() {
        val repository = MarvelCharactersRepository(marvelAPI)
        repository.getCharacters(offset = 30)

        verify(marvelAPI).getCharacters(any(), any(), any(), any(), eq(30))
    }

    @Test
    fun shouldConvertResponseToMarvelCharacter() {
        val responseItem = CharacterResponseItem(
                123,
                "bugman",
                "A Bug catcher super-hero",
                Thumbnail(
                        "https://s3-media2.fl.yelpcdn.com/bphoto/IAITw8luZ0Q77qw7EpHniw/ls",
                        "jpg"
                )
        )
        val repository = MarvelCharactersRepository(marvelAPI)
        val character = repository.convertResponseItem(responseItem)

        assertEquals(123, character.id)
        assertEquals("bugman", character.name)
        assertEquals("A Bug catcher super-hero", character.description)
        assertEquals("https://s3-media2.fl.yelpcdn.com/bphoto/IAITw8luZ0Q77qw7EpHniw/ls.jpg", character.imageUrl)
    }
}