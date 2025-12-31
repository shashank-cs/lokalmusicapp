package com.example.lokalmusicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MiniPlayer(
    playerViewModel: PlayerViewModel,
    onExpand: () -> Unit,
    onOpenQueue: () -> Unit
) {
    val song = playerViewModel.currentSong.value ?: return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onExpand() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🎨 Album Art
        AsyncImage(
            model = song.image.lastOrNull()?.url,
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.width(10.dp))

        // 🎵 Song Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
            Text(
                text = song.artists.primary.firstOrNull()?.name ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }

        // 📃 Queue
        Text(
            text = "≡",
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable { onOpenQueue() }
        )

        // ⏮ Previous
        Text(
            text = "⏮",
            modifier = Modifier
                .padding(end = 6.dp)
                .clickable { playerViewModel.playPrevious() }
        )

        // ▶ / ⏸ Play Pause
        Text(
            text = if (playerViewModel.isPlaying.value) "⏸" else "▶",
            modifier = Modifier
                .padding(end = 6.dp)
                .clickable { playerViewModel.togglePlayPause() }
        )

        // ⏭ Next
        Text(
            text = "⏭",
            modifier = Modifier
                .clickable { playerViewModel.playNext() }
        )
    }
}
