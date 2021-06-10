package fr.deezer.albums.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import fr.deezer.albums.model.Artist

class Converters {

    @TypeConverter
    fun stringFromArtist(value: String?): Artist? {
        return if (value == null) null else Gson().fromJson(value, Artist::class.java)
    }

    @TypeConverter
    fun artistToString(artist: Artist?): String? {
        return Gson().toJson(artist)
    }

}