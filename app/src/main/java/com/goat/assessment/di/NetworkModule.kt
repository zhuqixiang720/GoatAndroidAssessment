package com.goat.assessment.di

import com.goat.assessment.BuildConfig
import com.goat.assessment.api.ServiceApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val HTTP_CONNECT_TIMEOUT = 10L

@Module
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            // Only add HTTP logs for debug builds
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideUserServiceApi(client: OkHttpClient): ServiceApi {
        val serviceUrl = "${BuildConfig.WEB_BASE_URL}/${BuildConfig.SERVICE_PRIVATE_KEY}/"
        val retrofit = Retrofit.Builder()
            .baseUrl(serviceUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ServiceApi::class.java)
    }

    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}
