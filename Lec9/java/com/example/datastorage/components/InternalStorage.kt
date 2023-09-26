package com.example.datastorage.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.FileInputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalStorage(context: Context, modifier: Modifier) {

    var text by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    Text("Internal Storage", modifier = Modifier)
    Row {
        TextField(
            value = content,
            onValueChange = {
                content = it
            },
            label = { Text("Enter what ever u want") }, // Show a label above the text field
            placeholder = {}, // Show a placeholder when the text field is empty
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            context.openFileOutput("hello.txt", Context.MODE_PRIVATE).use {
                it.write(content.toByteArray())
            }
        }) {
            Text("Save")
        }
    }
    Button(onClick = {
        text = readFile(fileName = "hello.txt", context = context)

    }, modifier = Modifier) {
        Text("Load data from file")
    }
    Text(text = text, modifier = Modifier)
}

fun readFile(fileName: String, context: Context): String {

    val fis: FileInputStream = context.openFileInput(fileName)
    var byte = fis.read()
    val data: StringBuilder = StringBuilder()
    while (byte != -1) {
        // Append the byte as a char to the content
        data.append(byte.toChar())
        // Read the next byte
        byte = fis.read()
    }

    fis.close()
    return data.toString()

}