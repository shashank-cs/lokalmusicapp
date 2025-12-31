package com.example.lokalmusicplayer

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayerManager(
    private val context: Context
) {

    private var player: ExoPlayer? = null

    private fun startPlaybackService() {
        val intent = Intent(context, MusicPlaybackService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    private fun ensurePlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
        }
    }

    fun playSong(song: Song) {
        startPlaybackService()
        ensurePlayer()

        val mediaItem = MediaItem.fromUri(song.downloadUrl.last().url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    fun pauseOrResume() {
        player?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun isPlaying(): Boolean {
        return player?.isPlaying ?: false
    }
}
