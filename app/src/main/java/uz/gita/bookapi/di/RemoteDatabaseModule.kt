package uz.gita.bookapi.di

import android.content.Context
import com.mocklets.pluto.Pluto
import com.mocklets.pluto.PlutoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.bookapi.data.source.local.shared.SharedPref
import uz.gita.bookapi.data.source.remote.serivce.AuthApi
import uz.gita.bookapi.data.source.remote.serivce.BookApi
import uz.gita.bookapi.utils.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDatabaseModule {

        @Provides
        @Singleton
        fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun client(
        @ApplicationContext context: Context, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        Pluto.initialize(context)
        return OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("authorization", "Bearer ${SharedPref(context).token}")
                .build()

            chain.proceed(request)
        }
            .addInterceptor(loggingInterceptor)
            .addInterceptor(PlutoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    fun authApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun bookApi(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)

}