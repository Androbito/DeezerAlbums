package fr.deezer.albums.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class AlbumsResponse(
    @SerializedName("checksum")
    val checksum: String,
    @SerializedName("data")
    val `data`: List<Album>,
    @SerializedName("next")
    val next: String,
    @SerializedName("total")
    val total: Int
)

@Entity
data class Album(
    @ColumnInfo(name = "artist")
    @SerializedName("artist")
    val artist: Artist,
    @ColumnInfo(name = "available")
    @SerializedName("available")
    val available: Boolean,
    @ColumnInfo(name = "cover")
    @SerializedName("cover")
    val cover: String,
    @ColumnInfo(name = "cover_big")
    @SerializedName("cover_big")
    val coverBig: String,
    @ColumnInfo(name = "cover_medium")
    @SerializedName("cover_medium")
    val coverMedium: String,
    @ColumnInfo(name = "cover_small")
    @SerializedName("cover_small")
    val coverSmall: String,
    @ColumnInfo(name = "cover_xl")
    @SerializedName("cover_xl")
    val coverXl: String,
    @ColumnInfo(name = "explicit_lyrics")
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "link")
    @SerializedName("link")
    val link: String,
    @ColumnInfo(name = "nb_tracks")
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    @ColumnInfo(name = "record_type")
    @SerializedName("record_type")
    val recordType: String,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo(name = "time_add")
    @SerializedName("time_add")
    val timeAdd: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "tracklist")
    @SerializedName("tracklist")
    val tracklist: String,
    @ColumnInfo(name = "type")
    @SerializedName("type")
    val type: String
)

data class Artist(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    @SerializedName("tracklist")
    val tracklist: String,
    @SerializedName("type")
    val type: String
)