package io.rodrigo.agimarveltest.ui.characterdetails

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.rodrigo.agimarveltest.model.Promise
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.repository.ComicRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import java.util.concurrent.LinkedBlockingDeque


class CharacterDetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val comicRepository = mock<ComicRepository>()
    private val thanos = MarvelCharacter(
            700,
            "Thanos",
            Date(1524870000000),
            "The Mad Titan",
            "http://www.diarioonline.com.br/app/painel/modulo-noticia/img/imagensdb/original/destaque-421151-537bc6a21256d.jpg"
    )

    private val infinityGauntlet = Comic(
            50,
            "Infinity Gauntlet",
            null,
            null,
            null,
            null,
            null
    )

    @Before
    fun setup() {
        Locale.setDefault(Locale("en", "US"))
        whenever(comicRepository.getComics(700))
                .thenReturn(Promise.just(listOf(infinityGauntlet)))
    }

    @Test
    fun shouldUseCharactersData() {
        val viewModel = createViewModel()

        assertEquals(700, viewModel.id)
        assertEquals("Thanos", viewModel.name)
        assertEquals("Apr 27, 2018", viewModel.dateCreated)
        assertEquals("The Mad Titan", viewModel.description)
        assertEquals("http://www.diarioonline.com.br/app/painel/modulo-noticia/img/imagensdb/original/destaque-421151-537bc6a21256d.jpg", viewModel.imageUrl)
    }

    @Test
    fun shouldGetComicsFromRepository() {
        val viewModel = createViewModel()

        val queue = LinkedBlockingDeque<List<Comic>>(1)
        viewModel.comics.observeForever {
            queue.add(it)
        }
        val response = queue.pop()
        assertEquals(1, response.size)
        assertEquals(infinityGauntlet, response.first())
    }

    private fun createViewModel(): CharacterDetailsViewModel {
        val factory = CharacterDetailsViewModel.Factory(comicRepository)
        factory.character = thanos
        return factory.create(CharacterDetailsViewModel::class.java)
    }
}