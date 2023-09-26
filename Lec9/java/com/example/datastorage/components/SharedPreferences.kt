package com.example.datastorage.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedPreferences(context: Context, modifier: Modifier) {

    var name by remember { mutableStateOf("") }
    var sharedName by remember { mutableStateOf(context.getSharedPreferences("sharedPreferences", 0).getString("name", "None")!!) }



    Row {
        Text("Shared Preferences", modifier = Modifier)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = sharedName,
            modifier = Modifier
        )
    }
    Row {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text("Enter your name") }, // Show a label above the text field
            placeholder = { Text("Input your name") }, // Show a placeholder when the text field is empty
            leadingIcon = {
                Icon(
                    Icons.Rounded.AccountCircle,
                    "person icon"
                )
            } // Show an icon at the start of the text field
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            Log.i("InternalStorage: ", context.filesDir.toString())
            context.getSharedPreferences("sharedPreferences", 0).edit()
                .putString("name", name).apply()
            sharedName = context.getSharedPreferences("sharedPreferences", 0).getString("name", "None")!!
        }) {
            Text("Save")
        }
    }
}