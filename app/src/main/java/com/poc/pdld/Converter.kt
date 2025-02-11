package com.poc.pdld

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poc.pdld.data.Subjects

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromSubjectsList(subjects: List<Subjects>): String {
        return gson.toJson(subjects)
    }

    @TypeConverter
    fun toSubjectsList(subjectsString: String): List<Subjects> {
        val listType = object : TypeToken<List<Subjects>>() {}.type
        return gson.fromJson(subjectsString, listType)
    }

}