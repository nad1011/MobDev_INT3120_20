package com.example.datastorage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastorage.components.ExternalStorage
import com.example.datastorage.components.InternalStorage
import com.example.datastorage.components.SQLiteExample
import com.example.datastorage.components.SharedPreferences
import com.example.datastorage.ui.theme.DataStorageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context: MainActivity = this
            DataStorageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content(context = context, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun Content(context: Context, modifier: Modifier) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var tabIndex by remember { mutableStateOf(0) }

        TabRow(selectedTabIndex = tabIndex) {
            listOf(
                "Shared Preferences",
                "Internal",
                "External",
                "SQLite"
            ).forEachIndexed { index, title ->
                Tab(
                    selected = (tabIndex == index),
                    onClick = { tabIndex = index },
                    text = {
                        Text(
                            text = title,
                        )
                    },
                )
            }
        }
        when (tabIndex) {
            0 -> SharedPreferences(context = context, modifier = modifier)
            1 -> InternalStorage(context = context, modifier = modifier)
            2 -> ExternalStorage(context = context)
            3 -> SQLiteExample(context = context)
        }
    }
}