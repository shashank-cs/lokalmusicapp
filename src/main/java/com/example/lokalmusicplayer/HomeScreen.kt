package com.example.lokalmusicplayer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    playerViewModel: PlayerViewModel
) {
    var searchQuery by remember { mutableStateOf("") }

    // ✅ Snackbar state (CORRECT)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            // 🔍 Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search songs or artists") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                trailingIcon = {
                    Text(
                        text = "Search",
                        modifier = Modifier
                            .clickable {
                                if (searchQuery.isNotBlank()) {
                                    homeViewModel.searchSongs(searchQuery)
                                }
                            }
                            .padding(8.dp)
                    )
                }
            )

            // 🎵 Song list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                items(homeViewModel.songs.value) { song ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // ▶ Play immediately
                                playerViewModel.playNow(song)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = song.image.lastOrNull()?.url,
                            contentDescription = null,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(song.name, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                song.artists.primary.firstOrNull()?.name ?: "Unknown Artist",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        // ➕ Add to queue + snackbar
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add to queue",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    playerViewModel.addToQueue(song)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Added to queue",
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                }
                        )

                    }
                }
            }
        }

        // ✅ Snackbar host (CORRECT POSITION)
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 72.dp)
        )

    }
}
