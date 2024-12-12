package com.example.audiorecorder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.audiorecorder.ui.theme.AudiorecorderTheme

class MainActivity : ComponentActivity() {

    private val recorder by lazy{
        AudioRecorder(applicationContext)
    }

    private val player by lazy{
        AudioPlayer(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )

            var audioFilesState by remember { mutableStateOf(getAllRecordedFiles(applicationContext)) }
            var isRecording by remember { mutableStateOf(false) }

            AudiorecorderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                        AudioFileList(
                            player = player,
                            audioFilesState
                        )
                        Box(
                            modifier = Modifier.width(IntrinsicSize.Max)
                                .align(Alignment.BottomCenter),
                        ){
                            if(isRecording){
                                Icon(
                                    painter = painterResource(R.drawable.stop),
                                    contentDescription = "stop",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clickable {
                                        isRecording = false
                                        audioFilesState = getAllRecordedFiles(applicationContext)
                                        recorder.onStop()
                                    }
                                )
                            }else{
                                Icon(
                                    painter = painterResource(R.drawable.start),
                                    contentDescription = "start",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clickable {
                                        isRecording = true
                                        recorder.onStart()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.onStop()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AudiorecorderTheme {
        Greeting("Android")
    }
}