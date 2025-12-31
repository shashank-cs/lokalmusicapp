package com.example.lokalmusicplayer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.AsyncImage

@Composable
fun QueueScreen(
    playerViewModel: PlayerViewModel,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Up Next",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Close",
                modifier = Modifier.clickable { onClose() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(playerViewModel.queue) { index, song ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Album art
                    AsyncImage(
                        model = song.image.lastOrNull()?.url,
                        contentDescription = null,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Song info
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = song.name,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                        Text(
                            text = song.artists.primary.firstOrNull()?.name ?: "Unknown Artist",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }

                    // Move up
                    Text(
                        text = "↑",
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickable {
                                playerViewModel.moveQueueItem(index, index - 1)
                            }
                    )

                    // Move down
                    Text(
                        text = "↓",
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickable {
                                playerViewModel.moveQueueItem(index, index + 1)
                            }
                    )

                    // Remove
                    Text(
                        text = "✕",
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .clickable {
                                playerViewModel.removeFromQueueAt(index)
                            }
                    )
                }
            }
        }
    }
}
