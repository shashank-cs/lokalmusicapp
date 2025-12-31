
package com.example.lokalmusicplayer

data class ApiResponse(
    val data: DataBlock
)

data class DataBlock(
    val results: List<Song>
)

data class Song(
    val id: String,
    val name: String,
    val duration: Int,
    val image: List<Image>,
    val downloadUrl: List<DownloadUrl>,
    val artists: ArtistBlock,
    val album: Album
)

data class Image(val url: String)

data class DownloadUrl(val quality: String, val url: String)

data class ArtistBlock(val primary: List<Artist>)

data class Artist(val id: String, val name: String)

data class Album(
    val id: String,
    val name: String
)

