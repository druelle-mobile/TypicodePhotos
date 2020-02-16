package ovh.geoffrey_druelle.typicodephotos.di

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ovh.geoffrey_druelle.typicodephotos.data.remote.api.TypicodeApi
import ovh.geoffrey_druelle.typicodephotos.utils.BASE_URL
import ovh.geoffrey_druelle.typicodephotos.utils.CONNECT_TIMEOUT
import ovh.geoffrey_druelle.typicodephotos.utils.READ_TIMEOUT
import ovh.geoffrey_druelle.typicodephotos.utils.WRITE_TIMEOUT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModules = module {
    single { Cache(androidApplication().cacheDir, 20L * 1024 * 1024) }
    single(named("okhttp")) { provideOkHttpClient(get()) }
    single(named("retrofit")) { provideRetrofitClient(get(named("okhttp"))) }
    single(named("api")) { provideApiService(get(named("retrofit"))) }
}

fun provideOkHttpClient(cache: Cache): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(cache)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(apiInterceptor())
    }.build()
}

fun apiInterceptor() = Interceptor { chain ->
    chain.proceed(
        chain.request().newBuilder()
            .apply {
                header("Accept", "application/json")
                header("Content-Type", "application/json; charset=utf-8")
            }
            .build()
    )
}

fun provideRetrofitClient(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): TypicodeApi =
    retrofit.create(TypicodeApi::class.java)