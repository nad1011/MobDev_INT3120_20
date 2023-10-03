package com.example.contentprovider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.core.app.ActivityCompat
import com.example.contentprovider.components.ContactDeletionScreen
import com.example.contentprovider.components.ContactInsertionScreen
import com.example.contentprovider.components.ContactQueryScreen
import com.example.contentprovider.components.ContactUpdateScreen
import com.example.contentprovider.components.StudentInsert
import com.example.contentprovider.components.StudentQuery
import com.example.contentprovider.ui.theme.ContentProviderTheme
import com.example.contentprovider.viewmodel.ContactViewModel
import com.example.contentprovider.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.WRITE_CONTACTS,
                android.Manifest.permission.READ_CONTACTS
            ), 0
        )
        setContent {
            StudentContent()
//            UseContactContent()
        }
    }
}

@Composable
fun StudentContent() {
    ContentProviderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                var tabIndex by remember { mutableStateOf(0) }

                TabRow(selectedTabIndex = tabIndex) {
                    listOf(
                        "Insert",
                        "Query"
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
                    0 -> StudentQuery(
                        viewModel = StudentViewModel()
                    )
                    1 -> StudentInsert(
                        viewModel = StudentViewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun UseContactContent() {
    ContentProviderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                var tabIndex by remember { mutableStateOf(0) }

                TabRow(selectedTabIndex = tabIndex) {
                    listOf(
                        "Insert",
                        "Query",
                        "Delete",
                        "Update"
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
                    0 -> ContactInsertionScreen(
                        viewModel = ContactViewModel()
                    )
                    1 -> ContactQueryScreen(
                        viewModel = ContactViewModel()
                    )
                    2 -> ContactDeletionScreen(
                        viewModel = ContactViewModel()
                    )
                    3 -> ContactUpdateScreen(
                        viewModel = ContactViewModel()
                    )
                }
            }
        }
    }
}




