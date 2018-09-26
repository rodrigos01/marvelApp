package io.rodrigo.agimarveltest.ui.characterslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import io.rodrigo.agimarveltest.ui.Listing
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = mock<CharactersRepository>()
    private val characters = Listing<MarvelCharacter>(
            pagedList = mock(),
            pageSize = 10,
            status = mock(),
            refresh = {}
    )


    @Before
    fun setup() {
        whenever(repository.characters).thenReturn(characters)
    }

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