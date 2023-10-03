package com.example.contentprovider.viewmodel

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.contentprovider.Contact

class ContactViewModel: ViewModel() {

    var contactList by mutableStateOf<List<Contact>>(emptyList())

    fun insertContact(context: Context, contactName: String, contactPhoneNumber: String, contactEmail: String) {

        val contentResolver = context.contentResolver

        // Create a new contact
        val contactValues = ContentValues().apply {
            put(ContactsContract.Contacts.DISPLAY_NAME, contactName)
        }

        val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, contactValues)
        val rawContactId = rawContactUri?.let { ContentUris.parseId(it) }
        Log.e("rawContactId", rawContactId.toString())

        // Insert phone number
        val phoneValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, contactPhoneNumber)
            put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        }

        Log.e("phoneValues", contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneValues).toString())


        // Insert email
        val emailValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Email.ADDRESS, contactEmail)
            put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
        }

        Log.e("emailValues", contentResolver.insert(ContactsContract.Data.CONTENT_URI, emailValues).toString())
    }

    @SuppressLint("Range")
    fun queryContacts(context: Context) {
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use { cursor ->
            val contacts = mutableListOf<Contact>()
            while (cursor.moveToNext()) {
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val contact = Contact(contactId, displayName)
                contacts.add(contact)
            }
            contactList = contacts
        }
    }

    fun deleteContact(context: Context, contactId: String) {
        val contentResolver = context.contentResolver
        val contactUri = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI,
            contactId.toLong()
        )

        val deletedRows = contentResolver.delete(contactUri, null, null)

        if (deletedRows > 0) {
            Log.e("ContactViewModel", "Contact deleted")
        } else {
            Log.e("ContactViewModel", "Contact not deleted")
        }
    }
    fun updateContact(context: Context, contactId: String, newContactName: String) {
        val contentResolver = context.contentResolver
        val contactUri = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI,
            contactId.toLong()
        )

        val values = ContentValues().apply {
            put(ContactsContract.Contacts.DISPLAY_NAME, newContactName)
        }

        val updatedRows = contentResolver.update(contactUri, values, null, null)

        if (updatedRows > 0) {
            Log.e("ContactViewModel", "Contact updated")
        } else {
            Log.e("ContactViewModel", "Contact not updated")
        }
    }
}