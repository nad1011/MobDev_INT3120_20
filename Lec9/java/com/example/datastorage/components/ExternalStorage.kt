package com.example.datastorage.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalStorage(context: Context) {
    // on below line creating a variable for message.
    val message = remember {
        mutableStateOf("")
    }
    val txtMsg = remember {
        mutableStateOf("")
    }

    Column() {
        Text(
            text = "External Storage in Android",
        )

        TextField(
            value = message.value,
            onValueChange = { message.value = it },
            placeholder = { Text(text = "Enter your message") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val folder: File? = context.getExternalFilesDir("External")
                    val file = File(folder, "private.txt")
                    writeTextData(file, message.value, context)
                    message.value = ""
                    Toast.makeText(context, "Data saved privately", Toast.LENGTH_SHORT).show()
                },
            ) {
                Text(text = "Save Privately", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    val activity = context as Activity
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 23
                    )
                    val folder: File =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                    // Storing the data in file with name as geeksData.txt
                    val file = File(folder, "public.txt")
                    writeTextData(file, message.value, context)
                    message.value = ""
                    // displaying a toast message
                    Toast.makeText(context, "Data saved publicly..", Toast.LENGTH_SHORT).show()
                },
            ) {
                Text(text = "Save Publicly", textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Data will appear below : \n" + txtMsg.value,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val folder: File? = context.getExternalFilesDir("External")
                    val file = File(folder, "private.txt")
                    Log.i("file is exists", file.exists().toString())
                    val data = getdata(file)
                    if (data != null && data != "") {
                        txtMsg.value = data
                    } else {
                        txtMsg.value = "No Data Found"
                    }
                },

                ) {
                Text(text = "View Private", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    val folder =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(folder, "public.txt")
                    val data: String = getdata(file)
                    if (data != null && data != "") {
                        txtMsg.value = data
                    } else {
                        txtMsg.value = "No Data Found"
                    }
                },

                ) {
                Text(text = "View Public", textAlign = TextAlign.Center)
            }
        }
    }
}

private fun getdata(myfile: File): String {
    var bufferedReader: BufferedReader? = null
    // on below line reading data from file and returning it.
    try {
        // on below line checking if file exists and is not empty.
        if (myfile.exists() && myfile.length() > 0) {
            // on below line creating an input stream reader with UTF-8 encoding.
            val inputStreamReader = InputStreamReader(FileInputStream(myfile), "UTF-8")
            // on below line creating a buffered reader to read the file line by line.
            bufferedReader = BufferedReader(inputStreamReader)
            // on below line creating a string builder to append the lines.
            val stringBuilder = StringBuilder()
            // on below line using a for loop to read the file until it returns null.
            for (line in bufferedReader.lineSequence()) {
                // on below line appending the line and a newline character to the string builder.
                stringBuilder.append(line).append("\n")
            }
            // on below line returning the string from the string builder.
            return stringBuilder.toString()
        } else {
            // on below line returning an empty string if file does not exist or is empty.
            return ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (bufferedReader != null) {
            try {
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return ""
}

// on below line creating a method to write data to txt file.
private fun writeTextData(file: File, data: String, context: Context) {
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(data.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}