package lec8.components

import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ImplicitBroadcast() {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(
                    context, intent.extras?.getString("message") ?: "None", Toast.LENGTH_SHORT
                ).show()
            }
        }

        context.registerReceiver(
            receiver,
            IntentFilter("TEST_ACTION"),
            RECEIVER_EXPORTED
        )

        Log.i("Receiver", "Implicit receiver registered")

        onDispose {
            context.unregisterReceiver(receiver)
            Log.i("Receiver", "Implicit receiver disposed")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        IntentButton(
            onClick = {
                context.sendOrderedBroadcast(
                    Intent()
                        .apply {
                            action = "TEST_ACTION"
                            putExtra("message", "Implicit receiver called")
                        },
                    null
                )
            },
            text = "Broadcast",
            iconImage = Icons.Rounded.Share
        )
    }
}