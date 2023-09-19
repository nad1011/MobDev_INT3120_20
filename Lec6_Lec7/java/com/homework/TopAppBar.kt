package com.mobdevhomework

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(setOnDisplayPage: (Int) -> Unit) {

    var mDisplayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = "Menu Demo", color = Color.White)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF0a8f81)),
        actions = {

            // Creating Icon button for dropdown menu
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(
                    text = { Text(text = "Page 1") },
                    onClick = { setOnDisplayPage(0) }
                )

                DropdownMenuItem(
                    text = { Text(text = "Page 2") },
                    onClick = { setOnDisplayPage(1) }
                )
            }
        }
    )
}


