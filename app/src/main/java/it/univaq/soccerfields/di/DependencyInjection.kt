package it.univaq.soccerfields.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.univaq.soccerfields.common.BASE_URL
import it.univaq.soccerfields.data.local.FieldDatabase
import it.univaq.soccerfields.data.local.FieldRoomRepository
import it.univaq.soccerfields.data.remote.FieldRetrofitRepository
import it.univaq.soccerfields.data.remote.FieldService
import it.univaq.soccerfields.domain.repository.FieldLocalRepository
import it.univaq.soccerfields.domain.repository.FieldRemoteRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun fieldService(retrofit: Retrofit): FieldService =
        retrofit.create(FieldService::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun remoteRepository(repository: FieldRetrofitRepository): FieldRemoteRepository

    @Binds
    @Singleton
    abstract fun localRepository(repository: FieldRoomRepository): FieldLocalRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): FieldDatabase =
        Room.databaseBuilder(
                context,
                FieldDatabase::class.java,
                "field_database"
            )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun fieldDAO(database: FieldDatabase) = database.getFieldDAO()
}