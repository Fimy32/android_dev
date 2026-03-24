package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.gson.responseObject // for GSON - uncomment when needed
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var artist by remember { mutableStateOf("") }
            var results by remember { mutableStateOf("") }
            MyApplicationTheme {
                Column {
                    Text("Search For Artist")
                    TextField(
                        value = artist,
                        onValueChange = { artist = it }
                    )
                    Button(onClick = {print("TODO")}) {
                        Text("Submit")
                    }
                    Text("Results: $results")

                    NetworkComm(artist.toString())
                }
            }
        }
    }
}

@Composable
fun NetworkComm(artist: String) {
    var responseText by remember { mutableStateOf("") }
    Column {
        Button( onClick = {
            // URL which returns JSON describing points of interest
            var url = "http://10.0.2.2:3000/artist/" + artist
            url.httpGet().response { request, response, result ->

                when(result) {
                    is Result.Success -> {
                        // result.get() gives ByteArray, decode to string
                        responseText = result.get().decodeToString()
                    }

                    is Result.Failure -> {
                        // is failure if HTTP error
                        responseText = "ERROR ${result.error.message}"
                    }
                }
            }
        }) {
            Text("Get data from Web!")
        }
        Text(responseText)
    }
}



