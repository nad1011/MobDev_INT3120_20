package com.example.media

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.media.ui.theme.MediaTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LastLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapsInitializer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var fusedLocationClient: FusedLocationProviderClient
            val context = LocalContext.current
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            MediaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    PlayMusicFromSystemResource()
//                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//                    Location(context = context, fusedLocationClient = fusedLocationClient)
                    WebView(url = "https://www.google.com")
                }
            }
        }
    }
}

@Composable
fun PlayMusicFromSystemResource() {
    val mediaPlayer = remember { MediaPlayer() }

    mediaPlayer.apply {
        setDataSource("https://samplelib.com/lib/preview/mp3/sample-9s.mp3")
        prepareAsync()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            mediaPlayer.start()
        }) {
            Text(text = "Play")
        }
        Button(onClick = {
            mediaPlayer.pause()
        }) {
            Text(text = "Pause")
        }
    }
}

@Composable
fun Location(context: Context, fusedLocationClient: FusedLocationProviderClient) {
    var latitude by remember {
        mutableStateOf("null")
    }
    var longitude by remember {
        mutableStateOf("null")
    }
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.e("Location", "Permission not granted")
        return
    }
    val locationProvider = LocationManager.NETWORK_PROVIDER
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    locationManager.getLastKnownLocation(locationProvider)?.let {
        latitude = it.latitude.toString()
        longitude = it.longitude.toString()
    }

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            Log.e("Location", "Latitude: ${location.latitude} Longitude: ${location.longitude}")
        }
        .addOnFailureListener { e ->
            Log.e("Location", "Error: ${e.message}")
        }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Latitude: $latitude")
        Text(text = "Longitude: $longitude")
    }
}

@Composable
fun WebView(url: String) {
    AndroidView(
        factory = {
            android.webkit.WebView(it).apply {
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        update = { view ->
            view.loadUrl(url)
        }
    )
}