package com.example.datastorage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.datastorage.objects.FeedReaderContract.FeedEntry.COLUMN_AGE
import com.example.datastorage.objects.FeedReaderContract.FeedEntry.COLUMN_NAME
import com.example.datastorage.objects.FeedReaderContract.FeedEntry.TABLE_NAME

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Companion.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Companion.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    private fun isNameExists(name: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME=?", arrayOf(name))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun addNewInfor(
        name: String?,
        age: Int?,
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun readAllInfor(): ArrayList<InforModel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val inforList = ArrayList<InforModel>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE))
                val infor =
                    InforModel(name, age.toInt())
                inforList.add(infor)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return inforList

    }

    fun deleteInforByName(name: String): Boolean {
        if (!isNameExists(name)) return false
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME=?", arrayOf(name))
        db.close()
        return true
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME TEXT," +
                    "$COLUMN_AGE INTEGER)"
        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}