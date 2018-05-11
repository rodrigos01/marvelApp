package io.rodrigo.agimarveltest.model.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.switchMap
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdaper
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse
import io.rodrigo.agimarveltest.ui.Listing


class MarvelCharactersRepository(private val networkAdapter: NetworkAdaper) : CharactersRepository {

    private val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(30)
    private val factory = DataSourceFactory()

    override val characters = Listing(
            pagedList = LivePagedListBuilder(factory, 30).build(),
            status = factory.dataSource.switchMap { it.status },
            pageSize = 30
    )

    inner class CharactersDataSource(private val networkAdapter: NetworkAdaper) : PositionalDataSource<MarvelCharacter>() {

        val status = MutableLiveData<Listing.Status>()

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
            networkAdapter.getCharacters(params.loadSize, params.startPosition)
                    .onErrorReturn { CharactersResponse(0, emptyList()) }
                    .subscribe { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        callback.onResult(items)
                    }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MarvelCharacter>) {
            status.postValue(Listing.Status.STATUS_LOADING)
            networkAdapter.getCharacters(params.requestedLoadSize)
                    .onErrorReturn { CharactersResponse(0, emptyList()) }
                    .subscribe { response ->
                        val items = response.results.map { it.toMarvelCharacter() }
                        if (items.isNotEmpty()) {
                            status.postValue(Listing.Status.STATUS_INITIALIZED)
                            callback.onResult(items, 0, response.total)
                        }
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