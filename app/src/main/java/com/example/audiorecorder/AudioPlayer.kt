package com.example.audiorecorder

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(
    private val context: Context
) {
    private var player: MediaPlayer? = null
    private var onPlaybackFinishedListener: (() -> Unit)? = null

    fun onPlay(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            setOnCompletionListener {
                onStop()
                onPlaybackFinishedListener?.invoke()
            }
            start()
        }
    }

    fun onStop() {
        player?.stop()
        player?.release()
        player = null
    }

    fun setOnPlaybackFinishedListener(listener: () -> Unit) {
        onPlaybackFinishedListener = listener
    }
}
