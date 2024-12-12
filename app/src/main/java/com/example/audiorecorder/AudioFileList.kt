package com.example.audiorecorder

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
fun AudioFileList(player: AudioPlayer, audioFiles: List<File>) {
    val playingFile = remember { mutableStateOf<File?>(null) }
    val isPlaying = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (audioFiles.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(audioFiles) { file ->
                AudioFileItem(
                    fileName = file.name,
                    onClick = {
                        playingFile.value = file
                        isPlaying.value = true
                        player.onPlay(file)
                        player.setOnPlaybackFinishedListener {
                            isPlaying.value = false
                        }
                    }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No records available")
        }
    }

    if (isPlaying.value) {
        Toast.makeText(context, "Playing recorded file", Toast.LENGTH_SHORT).show()
        PlayingAnimationOverlay()
    }
}

