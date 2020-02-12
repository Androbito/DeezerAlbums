package fr.deezer.test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.deezer.test.model.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlbumDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}