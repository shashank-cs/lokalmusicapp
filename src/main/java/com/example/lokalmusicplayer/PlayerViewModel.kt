package com.example.lokalmusicplayer

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PlayerViewModel(
    context: Context
) : ViewModel() {

    private val playerManager = MusicPlayerManager(context)

    // ▶ Currently playing song
    val currentSong = mutableStateOf<Song?>(null)

    // ▶ Play / Pause state
    val isPlaying = mutableStateOf(false)

    // 🔥 QUEUE
    val queue = mutableStateListOf<Song>()
    private var currentIndex = -1

    /* ---------------- PLAYBACK ---------------- */

    // ▶ Play immediately (does NOT rebuild queue)
    fun playNow(song: Song) {
        currentSong.value = song
        playerManager.playSong(song)
        isPlaying.value = true

        // Initialize queue if empty
        if (queue.isEmpty()) {
            queue.add(song)
            currentIndex = 0
        }
    }

    fun togglePlayPause() {
        playerManager.pauseOrResume()
        isPlaying.value = playerManager.isPlaying()
    }

    /* ---------------- QUEUE ACTIONS ---------------- */

    // ➕ Add song to queue
    fun addToQueue(song: Song) {
        queue.add(song)
    }

    // ❌ Remove song from queue
    fun removeFromQueueAt(index: Int) {
        if (index !in queue.indices) return

        queue.removeAt(index)

        when {
            queue.isEmpty() -> {
                currentIndex = -1
                currentSong.value = null
            }
            index < currentIndex -> {
                currentIndex--
            }
            index == currentIndex -> {
                currentIndex = currentIndex.coerceAtMost(queue.lastIndex)
                currentSong.value = queue[currentIndex]
            }
        }
    }

    // 🔀 Reorder queue
    fun moveQueueItem(from: Int, to: Int) {
        if (from !in queue.indices || to !in queue.indices) return

        val song = queue.removeAt(from)
        queue.add(to, song)

        if (from == currentIndex) {
            currentIndex = to
        }
    }

    /* ---------------- NAVIGATION ---------------- */

    // ⏭ Play next song in queue
    fun playNext() {
        if (queue.isEmpty()) return

        currentIndex = (currentIndex + 1) % queue.size
        val song = queue[currentIndex]
        currentSong.value = song
        playerManager.playSong(song)
        isPlaying.value = true
    }

    // ⏮ Play previous song in queue
    fun playPrevious() {
        if (queue.isEmpty()) return

        currentIndex =
            if (currentIndex - 1 < 0) queue.lastIndex
            else currentIndex - 1

        val song = queue[currentIndex]
        currentSong.value = song
        playerManager.playSong(song)
        isPlaying.value = true
    }

    // 🎯 Expose current queue index (for UI highlight)
    fun currentQueueIndex(): Int = currentIndex
}
