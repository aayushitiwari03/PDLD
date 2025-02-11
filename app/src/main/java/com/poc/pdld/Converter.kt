package com.poc.pdld

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.poc.pdld.data.Subjects

class Converter {
    @TypeConverter
    fun fromMarksList(marks : List<Subjects>): String {
        return marks.joinToString (",")
    }

    @TypeConverter
    fun toMarksInt(marks : String): List<Subjects> {
        return marks.split(",").map {
            Subjects(it.toInt(), it.toInt(), it.toInt(), it.toInt(), it.toInt())
        }
    }
}