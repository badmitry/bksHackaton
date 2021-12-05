package com.badmitry.bkshackaton.di.modules

import android.app.Application
import com.badmitry.data.AndroidNetworkChecker
import com.badmitry.data.api.MoexApi
import com.badmitry.data.repositories.MoexRepositories
import com.badmitry.domain.repositories.IMoexRepositories
import com.badmitry.domain.repositories.INetworkChecker
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun networkChecker(app: Application): INetworkChecker = AndroidNetworkChecker(app)

    @Singleton
    @Provides
    fun gson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun client(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    @Provides
    @Singleton
    fun moexRetrofit(
        client: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl("https://iss.moex.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(MoexApi::class.java)

    @Provides
    @Singleton
    fun moexRepository(api: MoexApi): IMoexRepositories =
        MoexRepositories(api)
}