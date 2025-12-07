package com.kuldeep.notesapp.di

import android.content.Context
import androidx.room.Room
import com.kuldeep.notesapp.api.AuthInterceptor
import com.kuldeep.notesapp.api.NotesApi
import com.kuldeep.notesapp.api.UserApi
import com.kuldeep.notesapp.api.db.NoteDatabase
import com.kuldeep.notesapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory( GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOKHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }
    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit.Builder): UserApi {
        return retrofit.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteApi(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient): NotesApi{
        return retrofitBuilder.client(okHttpClient)
            .build().
            create(NotesApi::class.java)
    }
    // database provide
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
      return Room.databaseBuilder(
          context,
          NoteDatabase::class.java,
          "note_database"
      ).build()
    }
    @Singleton
    @Provides
    fun provideNoteDao(database: NoteDatabase) = database.noteDao()
}