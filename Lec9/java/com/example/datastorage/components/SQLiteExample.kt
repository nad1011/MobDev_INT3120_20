package com.example.datastorage.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.datastorage.DatabaseHandler
import com.example.datastorage.InforModel

@Composable
fun CourseList(infors: List<InforModel>) {
    LazyRow {
        infors.forEach() { infor ->
            item {
                Column {
                    Text(text = infor.name)
                    Text(text = infor.age.toString())
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SQLiteExample(context: Context) {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)
    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val deleteName = remember {
        mutableStateOf(TextFieldValue())
    }
    val age = remember {
        mutableStateOf(TextFieldValue())
    }
    val infors = remember {
        mutableStateOf(dbHandler.readAllInfor())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "SQlite Database in Android",
        )
        Spacer(modifier = Modifier.height(20.dp))
        CourseList(infors = infors.value)
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = "Enter your name") },
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = age.value,
            onValueChange = { age.value = it },
            placeholder = { Text(text = "Enter your age") },
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            dbHandler.addNewInfor(
                name.value.text,
                age.value.text.toInt(),
            )
            Toast.makeText(context, "Information Added to Database", Toast.LENGTH_SHORT).show()
            infors.value = dbHandler.readAllInfor()
        }) {
            // on below line adding a text for our button.
            Text(text = "Add Information to Database", color = Color.White)
        }

            Text(
                text = "Delete Information from Database by name",
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = deleteName.value,
                onValueChange = { deleteName.value = it },
                placeholder = { Text(text = "Enter the name") },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Button(onClick = {
                if (dbHandler.deleteInforByName(deleteName.value.text)) {
                    Toast.makeText(context, "Information Deleted from Database", Toast.LENGTH_SHORT)
                        .show()
                    infors.value = dbHandler.readAllInfor()
                } else {
                    Toast.makeText(context, "Information Not Found", Toast.LENGTH_SHORT).show()
                }
                deleteName.value = TextFieldValue("")
            }) {
                // on below line adding a text for our button.
                Text(text = "Delete", color = Color.White)
            }

    }
}