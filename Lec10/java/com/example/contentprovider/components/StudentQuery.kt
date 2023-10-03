package com.example.contentprovider.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.contentprovider.viewmodel.StudentViewModel

@Composable
fun StudentQuery(
    viewModel: StudentViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Query button
        Button(
            onClick = {
                viewModel.queryContacts(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Query Student")
        }

        LazyColumn {
            for (student in viewModel.studentList) {
                item {
                    Row {
                        Text(student.id.toString() ?: "No ID")
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(student.name ?: "No Name")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}