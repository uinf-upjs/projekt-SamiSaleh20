package com.example.compose_demo.data.room.models

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class DateConverter{
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }

    }
    @TypeConverter
    fun fromDate(date:Date?):Long?{
        return date?.time
    }
    @TypeConverter
    fun toDateFromString(dateString: String?): Date {
        return dateFormat.parse(dateString ?: "") ?: Date()
    }

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        return date?.let {
            dateFormat.format(it)
        }
    }
}