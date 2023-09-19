package com.homework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.homework.ui.theme.HomeworkTheme
import com.mobdevhomework.ContextMenu
import com.mobdevhomework.TopBar
import lec7.MainLec7Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkTheme {
                // A surface container using the 'background' color from the theme
                Content()
            }
        }
    }
}

@Preview
@Composable
fun Content() {

    val (onDisplayPage, setOnDisplayPage) = remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        TopBar(setOnDisplayPage)
        Surface(
            modifier = Modifier.fillMaxHeight(),

            ) {
            Page("Page ${onDisplayPage + 1}", LocalContext.current)
        }
    }
}

@Composable
fun Page(text: String, context: Context, modifier: Modifier = Modifier) {

    val intent = Intent(context, MainLec7Activity::class.java)

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = text )
            ContextMenu()
            Button(onClick = { ContextCompat.startActivity(context, intent, null) }) {
                Text(text = "Move to Lecture 7")
            }
        }

    }
}