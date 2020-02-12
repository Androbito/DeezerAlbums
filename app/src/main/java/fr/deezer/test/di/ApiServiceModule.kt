package fr.deezer.test.di

import dagger.Module
import dagger.Provides
import fr.deezer.test.api.ApiService
import fr.deezer.test.api.Constants
import fr.wawashi.wawapay.services.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiServiceModule{
    @Provides
    @Singleton
    fun provideApiService(): ApiService {val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient.build())
            .build()

        return retrofit.create(ApiService::class.java)
    }



}