package io.rodrigo.agimarveltest.model.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.switchMap
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter
import io.rodrigo.agimarveltest.ui.Listing


class MarvelCharactersRepository(private val networkAdapter: NetworkAdapter) : CharactersRepository {

    private val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(30)
            .build()

    @VisibleForTesting
    val factory = DataSourceFactory()

    override val characters = Listing(
            pagedList = LivePagedListBuilder(factory, config).build(),
            status = factory.dataSource.switchMap { it.status },
            refresh = {
                factory.dataSource.value?.invalidate()
            },
            pageSize = 30
    )

    inner class CharactersDataSource(private val networkAdapter: NetworkAdapter) : PositionalDataSource<MarvelCharacter>() {

        val status = MutableLiveData<Listing.Status>()

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
            networkAdapter.getCharacters(params.loadSize, params.startPosition)
                    .onSuccess { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        callback.onResult(items)
                    }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MarvelCharacter>) {
            status.postValue(Listing.Status.STATUS_LOADING)
            networkAdapter.getCharacters(params.requestedLoadSize)
                    .onSuccess { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        if (items.isNotEmpty()) {
                            status.postValue(Listing.Status.STATUS_INITIALIZED)
                            callback.onResult(items, 0, response.total)
                        }
                    }
                    .onError {
                        status.postValue(Listing.Status.STATUS_ERROR)
                    }

        }
    }

    inner class DataSourceFactory : DataSource.Factory<Int, MarvelCharacter>() {

        val dataSource = MutableLiveData<CharactersDataSource>()

        override fun create(): DataSource<Int, MarvelCharacter> = CharactersDataSource(networkAdapter)
                .also {
                    dataSource.postValue(it)
                }
    }
}