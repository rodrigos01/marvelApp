package io.rodrigo.agimarveltest.dependencyinjection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapterImpl
import io.rodrigo.agimarveltest.model.network.authorization.AuthorizationProviderImpl
import io.rodrigo.agimarveltest.model.network.authorization.DefaultTimestampGenerator
import io.rodrigo.agimarveltest.model.network.authorization.MD5HashGenerator
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import io.rodrigo.agimarveltest.model.repository.ComicRepository
import io.rodrigo.agimarveltest.model.repository.MarvelCharactersRepository
import io.rodrigo.agimarveltest.model.repository.MarvelComicRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
    }

    @Provides
    fun providesApi(gson: Gson, okHttpClient: OkHttpClient): MarvelAPI {
        return Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(MarvelAPI::class.java)
    }

    @Provides
    fun providesNetworkAdapter(api: MarvelAPI): NetworkAdapter {
        val timestampGenerator = DefaultTimestampGenerator()
        val hashGenerator = MD5HashGenerator()
        val authorizationProvider = AuthorizationProviderImpl(
                NetworkAdapterImpl.PUBLIC_KEY,
                NetworkAdapterImpl.PRIVATE_KEY,
                hashGenerator,
                timestampGenerator)
        return NetworkAdapterImpl(authorizationProvider, api)
    }

    @Provides
    fun providesCharactersRepository(networkAdapter: NetworkAdapter): CharactersRepository {
        return MarvelCharactersRepository(networkAdapter)
    }

    @Provides
    fun providesComicRepository(networkAdapter: NetworkAdapter): ComicRepository {
        return MarvelComicRepository(networkAdapter)
    }
}