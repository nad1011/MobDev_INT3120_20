package com.example.contentprovider.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.contentprovider.DatabaseHandler
import com.example.contentprovider.Student
import com.example.contentprovider.StudentProvider

class StudentViewModel : ViewModel() {
    var studentList by mutableStateOf<List<Student>>(emptyList())

    fun insertStudent(context: Context, name: String, id: Int) {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(DatabaseHandler.Student.COLUMN_NAME, name)
            put(DatabaseHandler.Student.COLUMN_ID, id)
        }
        Log.e(
            "StudentViewModel",
            contentResolver.insert(StudentProvider.CONTENT_URI, contentValues).toString()
        )

    }

    @SuppressLint("Range")
    fun queryContacts(context: Context) {
        val contentResolver = context.contentResolver
        val projection = arrayOf(
            DatabaseHandler.Student.COLUMN_ID,
            DatabaseHandler.Student.COLUMN_NAME
        )
        val cursor =
            contentResolver.query(StudentProvider.CONTENT_URI, projection, null, null, null)
        cursor?.use { cursor ->
            val students = mutableListOf<Student>()
            while (cursor.moveToNext()) {
                val studentId = cursor.getLong(cursor.getColumnIndex(DatabaseHandler.Student.COLUMN_ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(DatabaseHandler.Student.COLUMN_NAME))

                val student = Student(studentId, name)
                students.add(student)
            }
            studentList = students
        }
    }
}