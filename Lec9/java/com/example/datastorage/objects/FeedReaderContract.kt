package com.example.datastorage.objects

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "Information"
        const val COLUMN_NAME = "Name"
        const val COLUMN_AGE = "Age"
    }
}