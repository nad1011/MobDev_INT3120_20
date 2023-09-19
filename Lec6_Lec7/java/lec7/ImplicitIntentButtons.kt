package lec7

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImplicitIntentButtons(modifier: Modifier) {

    val context = LocalContext.current

    val browserIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/"))
    val dialIntent = Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0972949496"))
    val searchIntent = Intent(Intent.ACTION_WEB_SEARCH).putExtra("query", "Android")
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }
    val sendSmsIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:0972949496")
        putExtra("sms_body", "Hello")
    }
    val showPictureIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/pictures/*"
    }
    val showContactIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("content://contacts/people/")
    }
    val showMapIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California")
    }
    val openMusicIntent = Intent("android.intent.action.MUSIC_PLAYER")

    Column() {
        Button(onClick = {
            context.startActivity(browserIntent)
        }) {
            Text(text = "Open a web page")
        }
        Button(onClick = {
            context.startActivity(dialIntent)
        }) {
            Text(text = "Call Dad")
        }
        Button(onClick = {
            context.startActivity(searchIntent)
        }) {
            Text(text = "Search for Android")
        }
        Button(onClick = {
            context.startActivity(Intent.createChooser(shareIntent, "Share with"))
        }) {
            Text(text = "Share")
        }
        Button(onClick = {
            context.startActivity(sendSmsIntent)
        }) {
            Text(text = "Send SMS")
        }
        Button(onClick = {
            context.startActivity(showPictureIntent)
        }) {
            Text(text = "Show picture")
        }
        Button(onClick = {
            context.startActivity(showContactIntent)
        }) {
            Text(text = "Show contact")
        }
        Button(onClick = {
            context.startActivity(showMapIntent)
        }) {
            Text(text = "Show Google Directions")
        }
        Button(onClick = {
            context.startActivity(openMusicIntent)
        }) {
            Text(text = "Open Music Player")
        }
    }
}