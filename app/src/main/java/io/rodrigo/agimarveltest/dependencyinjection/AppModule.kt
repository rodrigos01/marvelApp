package io.rodrigo.agimarveltest.dependencyinjection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdaper
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapterImpl
import io.rodrigo.agimarveltest.model.network.authorization.AuthorizationProviderImpl
import io.rodrigo.agimarveltest.model.network.authorization.DefaultTimestampGenerator
import io.rodrigo.agimarveltest.model.network.authorization.MD5HashGenerator
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import io.rodrigo.agimarveltest.model.repository.MarvelCharactersRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
                .create()
    }

    @Provides
    fun providesApi(gson: Gson, okHttpClient: OkHttpClient): MarvelAPI {
        return Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(MarvelAPI::class.java)
    }

    @Provides
    fun providesNetworkAdapter(api: MarvelAPI): NetworkAdaper {
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
    fun providesCharactersRepository(networkAdaper: NetworkAdaper): CharactersRepository {
        return MarvelCharactersRepository(networkAdaper)
    }
}