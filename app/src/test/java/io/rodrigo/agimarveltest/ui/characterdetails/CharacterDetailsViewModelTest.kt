package io.rodrigo.agimarveltest.ui.characterdetails

import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


class CharacterDetailsViewModelTest {

    @Before
    fun setup() {
        Locale.setDefault(Locale("en", "US"))
    }

    @Test
    fun shouldUseCharactersData() {
        val thanos = MarvelCharacter(
                700,
                "Thanos",
                Date(1524870000000),
                "The Mad Titan",
                "http://www.diarioonline.com.br/app/painel/modulo-noticia/img/imagensdb/original/destaque-421151-537bc6a21256d.jpg"
        )

        val viewModel = createViewModel(thanos)

        assertEquals(700, viewModel.id)
        assertEquals("Thanos", viewModel.name)
        assertEquals("Apr 27, 2018", viewModel.dateCreated)
        assertEquals("The Mad Titan", viewModel.description)
        assertEquals("http://www.diarioonline.com.br/app/painel/modulo-noticia/img/imagensdb/original/destaque-421151-537bc6a21256d.jpg", viewModel.imageUrl)
    }

    private fun createViewModel(marvelCharacter: MarvelCharacter): CharacterDetailsViewModel {
        val factory = CharacterDetailsViewModel.Factory()
        factory.character = marvelCharacter
        return factory.create(CharacterDetailsViewModel::class.java)
    }
}