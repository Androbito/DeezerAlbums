package fr.deezer.test.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import fr.deezer.test.db.AlbumDao
import fr.deezer.test.db.AlbumDb
import javax.inject.Singleton

@Module
class AppModule(private val mApplication: Application) {
    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideDb(app: Application): AlbumDb {
        return Room.databaseBuilder(app, AlbumDb::class.java, "albums_database")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    internal fun provideAlbumDao(db: AlbumDb): AlbumDao {
        return db.albumDao()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return Gson()
    }
}