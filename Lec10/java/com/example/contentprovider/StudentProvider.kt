package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentResolver.NOTIFY_DELETE
import android.content.ContentResolver.NOTIFY_INSERT
import android.content.ContentResolver.NOTIFY_UPDATE
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri

class StudentProvider : ContentProvider() {

    private lateinit var database: DatabaseHandler

    override fun onCreate(): Boolean {
        database = DatabaseHandler(context!!)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        if (uriMatcher.match(uri) != TABLE_CODE) throw IllegalArgumentException("Can not insert $uri")
        else {
            val databaseWriter = database.writableDatabase
            val id = databaseWriter.insert(DatabaseHandler.TABLE_NAME, null, values)
            if (id == -1L) throw SQLException("Can not insert into $uri")
            context!!.contentResolver.notifyChange(uri, null, NOTIFY_INSERT)
            return ContentUris.withAppendedId(CONTENT_URI, id)
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val dbReader = database.readableDatabase

        if (uriMatcher.match(uri) != TABLE_CODE && uriMatcher.match(uri) != ITEM_CODE)
            throw IllegalArgumentException("Can not query $uri")
        else {
            val cursor: Cursor? = if (uriMatcher.match(uri) == TABLE_CODE ) {
                dbReader.query(
                    DatabaseHandler.TABLE_NAME, projection,
                    selection, selectionArgs,
                    null, null, sortOrder
                )
            } else if (uriMatcher.match(uri) == ITEM_CODE ) {
                dbReader.query(
                    DatabaseHandler.TABLE_NAME, projection,
                    "${DatabaseHandler.Student.COLUMN_ID}=?", arrayOf(uri.lastPathSegment),
                    null, null, sortOrder
                )
            } else {
                null
            }
            cursor?.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val dbWriter = database.writableDatabase

        val rowsUpdated: Int = if (uriMatcher.match(uri) == TABLE_CODE) {
            dbWriter.update(
                DatabaseHandler.TABLE_NAME, values,
                selection, selectionArgs
            )
        } else if (uriMatcher.match(uri) == ITEM_CODE) {
            dbWriter.update(
                DatabaseHandler.TABLE_NAME, values,
                "${DatabaseHandler.Student.COLUMN_ID}=?", arrayOf(uri.lastPathSegment)
            )
        } else {
            throw IllegalArgumentException("Update is not supported for $uri")
        }
        context!!.contentResolver.notifyChange(uri, null, NOTIFY_UPDATE)
        return rowsUpdated
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val dbWriter = database.writableDatabase

        val rowsDeleted: Int = if (uriMatcher.match(uri) == TABLE_CODE) {
            dbWriter.delete(
                DatabaseHandler.TABLE_NAME,
                selection, selectionArgs
            )
        } else if (uriMatcher.match(uri) == ITEM_CODE) {
            dbWriter.delete(
                DatabaseHandler.TABLE_NAME,
                "${DatabaseHandler.Student.COLUMN_ID}=?", arrayOf(uri.lastPathSegment)
            )
        } else {
            throw IllegalArgumentException("Delete is not supported for $uri")
        }
        context!!.contentResolver.notifyChange(uri, null, NOTIFY_DELETE)
        return rowsDeleted

    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            TABLE_CODE -> "vnd.android.cursor.dir/student"
            ITEM_CODE -> "vnd.android.cursor.item/student"
            else -> throw IllegalArgumentException("Unsupported uri: $uri")
        }
    }

    companion object {
        private const val AUTHORITY = "com.example.contentprovider.provider"
        private const val TABLE_CODE = 1
        private const val ITEM_CODE = 2

        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/${DatabaseHandler.TABLE_NAME}")
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "student", TABLE_CODE)
            addURI(AUTHORITY, "student/#", ITEM_CODE)
        }
    }
}


