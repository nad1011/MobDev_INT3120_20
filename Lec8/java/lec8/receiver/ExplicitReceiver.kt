package lec8.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(
            context, intent.extras?.getString("message") ?: "None", Toast.LENGTH_SHORT
        ).show()
    }
}