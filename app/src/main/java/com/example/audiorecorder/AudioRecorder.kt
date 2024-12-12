package com.example.audiorecorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import java.io.File

class AudioRecorder(
    private val context: Context
) {
    private var recorder : MediaRecorder? = null

    private fun createRecorder( ): MediaRecorder{
        return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            MediaRecorder(context)
        }else{
            MediaRecorder()
        }
    }

    private fun getPermanentFile(): File {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        if (dir?.exists() == false) {
            dir.mkdirs()
        }
        return File(dir, "audio_${System.currentTimeMillis()}.aac")
    }

    fun onStart(){
        onStop()
        val outputFile = getPermanentFile()
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)
            prepare()
            start()
            recorder = this
        }
    }

    fun onStop(){
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}