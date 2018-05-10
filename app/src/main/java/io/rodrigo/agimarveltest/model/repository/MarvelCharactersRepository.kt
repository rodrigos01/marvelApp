package io.rodrigo.agimarveltest.model.repository

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdaper
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse


class MarvelCharactersRepository(private val networkAdapter: NetworkAdaper) : CharactersRepository {

    override val characters = LivePagedListBuilder(DataSourceFactory(), PagedList.Config.Builder()
            .setPrefetchDistance(15)
            .setPageSize(30)
            .build()).build()

    inner class CharactersDataSource(private val networkAdapter: NetworkAdaper) : PositionalDataSource<MarvelCharacter>() {

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
            networkAdapter.getCharacters(params.loadSize, params.startPosition)
                    .onErrorReturn { CharactersResponse(0, emptyList()) }
                    .subscribe { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        callback.onResult(items)
                    }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MarvelCharacter>) {
            networkAdapter.getCharacters(params.pageSize)
                    .onErrorReturn { CharactersResponse(0, emptyList()) }
                    .subscribe { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        if (items.isNotEmpty()) {
                            callback.onResult(items, 0, response.total)
                        }
                    }

        }
    }

    inner class DataSourceFactory : DataSource.Factory<Int, MarvelCharacter>() {
        override fun create(): DataSource<Int, MarvelCharacter> = CharactersDataSource(networkAdapter)
    }
}