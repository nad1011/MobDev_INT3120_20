package lec8.components

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import lec8.receiver.ExplicitReceiver


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ExplicitBroadcast() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        IntentButton(
            onClick = {
                context.sendBroadcast(
                    Intent(context, ExplicitReceiver::class.java)
                        .apply { putExtra("message", "Explicit broadcast called!") }
                )
            },
            text = "Broadcast",
            iconImage = Icons.Rounded.Share
        )
    }
}
