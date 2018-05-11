package io.rodrigo.agimarveltest.ui.characterslist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersListViewModelTest {

    private val repository = mock<CharactersRepository>()

    @Test
    fun charactersShouldBeRepositoryCharacters() {
        val viewModel = createViewModel()

        verify(repository).characters
    }

    @Test
    fun itemsWithSameIdShouldBeTheSame() {
        val cap = MarvelCharacter(
                616,
                "Captain America",
                null,
                null,
                null
        )
        val nomad = MarvelCharacter(
                616,
                "Nomad",
                null,
                null,
                null
        )
        val viewModel = createViewModel()
        assert(viewModel.itemCallback.areItemsTheSame(cap, nomad))
    }

    @Test
    fun itemsWithSameDataShouldBeTheSame() {
        val cap = MarvelCharacter(
                616,
                "Captain America",
                null,
                null,
                null
        )
        val nomad = MarvelCharacter(
                616,
                "Captain America",
                null,
                null,
                null
        )
        val viewModel = createViewModel()
        assert(viewModel.itemCallback.areContentsTheSame(cap, nomad))
    }

    private fun createViewModel() = CharactersListViewModel(repository)
}