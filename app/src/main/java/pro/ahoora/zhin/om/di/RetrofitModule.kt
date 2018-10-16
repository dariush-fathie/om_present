package pro.ahoora.zhin.om.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.ahoora.zhin.om.repositories.local.RESTApi
import pro.ahoora.zhin.om.util.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    @Provides
    @Singleton
    fun provideRESTApi(retrofit: Retrofit): RESTApi {
        return retrofit.create(RESTApi::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()
    }

}