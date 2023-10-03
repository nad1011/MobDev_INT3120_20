package com.example.contentprovider.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.contentprovider.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUpdateScreen(
    viewModel: ContactViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        var contactId by remember { mutableStateOf("") }
        var contactName by remember { mutableStateOf("") }

        // Input fields for contact ID and new name
        OutlinedTextField(
            value = contactId,
            onValueChange = { contactId = it },
            label = { Text("Contact ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("New Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Update button
        Button(
            onClick = {
                viewModel.updateContact(context, contactId, contactName)
                contactId = ""
                contactName = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Update Contact")
        }
    }
}