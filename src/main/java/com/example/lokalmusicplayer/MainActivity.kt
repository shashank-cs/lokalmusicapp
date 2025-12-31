package com.example.lokalmusicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔔 Request notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setContent {

            // ViewModels
            val homeViewModel = remember { HomeViewModel() }
            val playerViewModel = remember { PlayerViewModel(this) }

            // UI state
            val showQueue = remember { mutableStateOf(false) }
            val showFullPlayer = remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxSize()) {

                // 🏠 HOME SCREEN (hidden when queue is open)
                if (!showQueue.value) {
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        playerViewModel = playerViewModel
                    )
                }

                // 📃 QUEUE SCREEN (FULL OVERLAY)
                if (showQueue.value) {
                    QueueScreen(
                        playerViewModel = playerViewModel,
                        onClose = { showQueue.value = false }
                    )
                }

                // 🎵 MINI PLAYER (always visible)
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    MiniPlayer(
                        playerViewModel = playerViewModel,
                        onExpand = { showFullPlayer.value = true },
                        onOpenQueue = { showQueue.value = true }
                    )
                }

                // 🎧 FULL PLAYER (optional – if you have it)
                if (showFullPlayer.value) {
                    FullPlayerScreen(
                        playerViewModel = playerViewModel,
                        onClose = { showFullPlayer.value = false }
                    )
                }
            }
        }
    }
}
