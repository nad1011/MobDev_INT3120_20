package com.mobdevhomework

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContextMenu() {
    var expanded by remember { mutableStateOf(false) }
    var expandedNestedMenu by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    Surface(
        color = Color.LightGray,
        modifier = Modifier.height(40.dp).width(80.dp).pointerInput(Unit) {
        detectTapGestures(
            onPress = { /* Called when the gesture starts */ },
            onDoubleTap = { /* Called on Double Tap */ },
            onLongPress = { expanded = true },
            onTap = { Toast.makeText(mContext, "Tap", Toast.LENGTH_SHORT).show() }
        )
    }) {
        Text(text = "Test", textAlign = TextAlign.Center,modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            // Creating dropdown menu item, on click
            // would create a Toast message
            DropdownMenuItem(
                text = { Text(text = "Test 1") },
                onClick = { expanded = false }
            )

            DropdownMenuItem(
                text = { Text(text = "Test nested 2") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "More",
//                        modifier = Modifier
//                            .height(24.dp)
//                            .width(24.dp)
                    )
                },
                onClick = { expandedNestedMenu = true }
            )
        }
        DropdownMenu(
            expanded = expandedNestedMenu,
            onDismissRequest = { expandedNestedMenu = false }
        ) {

            // Creating dropdown menu item, on click
            // would create a Toast message
            DropdownMenuItem(
                text = { Text(text = "Test Open Nested Menu") },
                onClick = { expandedNestedMenu = false }
            )

        }
    }
}
