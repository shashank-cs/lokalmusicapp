package com.example.lokalmusicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable


@Composable
fun FullPlayerScreen(
    playerViewModel: PlayerViewModel,
    onClose: () -> Unit
) {
    val song = playerViewModel.currentSong.value ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // 🎨 Album Art
        AsyncImage(
            model = song.image.lastOrNull()?.url,
            contentDescription = null,
            modifier = Modifier
                .size(260.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 🎵 Song Name
        Text(
            text = song.name,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 👤 Artist
        Text(
            text = song.artists.primary.firstOrNull()?.name ?: "Unknown Artist",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ▶️ Play / Pause
        Text(
            text = if (playerViewModel.isPlaying.value) "❚❚ Pause" else "▶ Play",
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .padding(horizontal = 32.dp, vertical = 12.dp)
                .clickable {
                    playerViewModel.togglePlayPause()
                },
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ❌ Close
        Text(
            text = "Close",
            color = Color.Gray,
            modifier = Modifier.clickable { onClose() }
        )
    }
}
