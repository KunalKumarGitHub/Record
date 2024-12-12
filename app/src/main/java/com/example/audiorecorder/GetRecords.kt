package com.example.audiorecorder

import android.content.Context
import android.os.Environment
import java.io.File

fun getAllRecordedFiles(context: Context): List<File> {
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    return dir?.listFiles()?.toList() ?: emptyList()
}