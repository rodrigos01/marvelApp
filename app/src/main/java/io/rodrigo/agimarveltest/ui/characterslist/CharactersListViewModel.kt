package io.rodrigo.agimarveltest.ui.characterslist

import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import io.rodrigo.agimarveltest.model.data.CharacterList
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    private val config = PagedList.Config.Builder()
            .setPrefetchDistance(15)
            .setPageSize(30)
            .build()

    val characters = LivePagedListBuilder(CharactersDataSourceFactory(), config).build()


    inner class CharactersDataSource : PositionalDataSource<MarvelCharacter>() {
        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
            charactersRepository.getCharacters(params.loadSize, params.startPosition)
                    .onErrorReturn { CharacterList(emptyList(), 0) }
                    .subscribe { items ->
                        callback.onResult(items)
                    }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MarvelCharacter>) {
            charactersRepository.getCharacters(params.pageSize)
                    .onErrorReturn { CharacterList(emptyList(), 0) }
                    .subscribe { items ->
                        if (items.isNotEmpty()) {
                            callback.onResult(items, 0, items.total)
                        }
                    }

        }

    }

    inner class CharactersDataSourceFactory : DataSource.Factory<Int, MarvelCharacter>() {
        override fun create(): DataSource<Int, MarvelCharacter> = CharactersDataSource()
    }

}