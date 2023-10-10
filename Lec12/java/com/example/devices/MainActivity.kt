package com.example.devices

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.devices.ui.theme.DevicesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevicesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    EnvironmentalSensor(context = this@MainActivity)
//                    NetworkTest(context = this@MainActivity)
//                    TelephoneService(context = this@MainActivity)
//                      TakePic(context = this@MainActivity)
                    BluetoothService(context = this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun BluetoothService(context: Context) {

    val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    var isBluetoothEnabled by remember {
        mutableStateOf(
            bluetoothAdapter?.isEnabled
        )
    }
    var discoverability by remember {
        mutableStateOf(
            when (
                try {
                    bluetoothAdapter?.scanMode
                } catch (_: SecurityException) {
                    BluetoothAdapter.SCAN_MODE_NONE
                }
            ) {
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> "Connectable Discoverable"
                BluetoothAdapter.SCAN_MODE_CONNECTABLE -> "Connectable"
                else -> "None"
            }
        )
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                    BluetoothAdapter.STATE_OFF -> {
                        Toast.makeText(context, "Bluetooth Off", Toast.LENGTH_SHORT).show()
                        isBluetoothEnabled = false
                    }

                    BluetoothAdapter.STATE_ON -> {
                        Toast.makeText(context, "Bluetooth On", Toast.LENGTH_SHORT).show()
                        isBluetoothEnabled = true
                    }
                }
            } else if (intent?.action == BluetoothAdapter.ACTION_SCAN_MODE_CHANGED) {
                discoverability = when (intent.getIntExtra(
                    BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR
                )) {
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> "Connectable Discoverable"
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> "Connectable"
                    else -> "None"
                }
            }
        }
    }
    context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Is Bluetooth Enable: ${isBluetoothEnabled.toString()}")
        Text(text = "Discoverability: $discoverability")
    }
}

@Composable
fun TakePic(context: Context) {
    var picPath by remember { mutableStateOf("") }

    val cameraIntent = Intent("android.media.action.IMAGE_CAPTURE")


    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.media.action.IMAGE_CAPTURE") {
                picPath = intent.getStringExtra("picPath") ?: ""
                Toast.makeText(context, "Pic Path: $picPath", Toast.LENGTH_SHORT).show()
            }
        }
    }
    context.registerReceiver(receiver, IntentFilter("com.example.devices.TAKE_PIC"))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            context.startActivity(cameraIntent)
        }) {
            Text(text = "Take Pic")
        }
        Text(text = "Pic Path: $picPath")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelephoneService(context: Context) {

    var sender by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
                val smsMessages =
                    Telephony.Sms.Intents.getMessagesFromIntent(intent)
                smsMessages.forEach { sms ->
                    sender = sms.originatingAddress
                    message = sms.messageBody
                }
            }
        }
    }
    context.registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sender: $sender")

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Message: $message")
    }
}

@Composable
fun NetworkTest(context: Context) {
    var networkStatus by remember { mutableStateOf("Unknown") }
    var networkType by remember { mutableStateOf("Unknown") }

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            CoroutineScope(Dispatchers.Main).launch { networkStatus = "Available" }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            CoroutineScope(Dispatchers.Main).launch {
                networkStatus = "Lost"
                networkType = "Unknown"
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            CoroutineScope(Dispatchers.Main).launch {
                networkType = when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
                    else -> "Unknown"
                }
            }
        }
    }

    connectivityManager.registerDefaultNetworkCallback(networkCallback)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Network Status: $networkStatus")
        Text(text = "Network Type: $networkType")
    }
}

@Composable
fun EnvironmentalSensor(context: Context) {
    var sensorTemperature by remember { mutableStateOf(0.0f) }
    var sensorHumidity by remember { mutableStateOf(0.0f) }

    var sensorManager: SensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

    if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
        val temperatureSensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                Log.e("MainActivity", "Accuracy Changed")
            }

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    sensorTemperature = event.values[0]
                }
            }
        }
        sensorManager.registerListener(
            temperatureSensorEventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        DisposableEffect(key1 = sensorManager) {
            onDispose {
                sensorManager.unregisterListener(temperatureSensorEventListener)
            }
        }
    } else {
        Log.e("MainActivity", "Temperature Sensor Not Found")
    }

    if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
        val humidSensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                Log.e("MainActivity", "Accuracy Changed")
            }

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    sensorHumidity = event.values[0]
                }
            }
        }
        sensorManager.registerListener(
            humidSensorEventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
            SensorManager.SENSOR_DELAY_UI
        )
        DisposableEffect(key1 = sensorManager) {
            onDispose {
                sensorManager.unregisterListener(humidSensorEventListener)
            }
        }
    } else {
        Log.e("MainActivity", "Humid Sensor Not Found")
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Temperature: $sensorTemperature")
        Text(text = "Humidity: $sensorHumidity")
    }
}