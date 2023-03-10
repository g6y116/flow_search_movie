package com.flow.hugh.di

import android.content.Context
import androidx.room.Room
import com.flow.hugh.Const
import com.flow.hugh.datasource.AppDatabase
import com.flow.hugh.datasource.api.MovieApi
import com.flow.hugh.datasource.dao.RecentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton @Provides
    fun provideRecentDao(database: AppDatabase): RecentDao = database.getRecentDao()

    @Singleton @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Singleton @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, Const.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton @Provides
    fun provideInterceptor (): Interceptor {
        return Interceptor {
            it.proceed(
                it.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", Const.CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", Const.CLIENT_SECRET)
                    .build()
            )
        }
    }

    @Singleton @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(provideInterceptor())
            .build()
    }

    @Singleton @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Const.BASE_URL)
            .build()
    }
}