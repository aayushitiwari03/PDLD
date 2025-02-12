package com.poc.pdld.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.poc.pdld.Converter
import org.jetbrains.annotations.NotNull
import javax.security.auth.Subject


@Entity(tableName = "student_results")
data class Results
(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "student_name")
    val name:String,

    @ColumnInfo(name = "student_class")
    val clas : String,

    @ColumnInfo(name = "student_father_name")
    val fatherName : String,

    @ColumnInfo(name = "student_mother_name")
    val motherName : String,

    @ColumnInfo(name = "student_marks")
    val marks : List<Subjects>,

    @ColumnInfo(name = "student_total_marks")
    val totalMarks : Int,

    @ColumnInfo(name = "student_roll_number")
    val rollNo : Int,


    @ColumnInfo(name = "last_synced")
    val lastUpdated : Long?,


    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean


)

data class Subjects(
    val maths: Int,
    val science: Int,
    val english:Int,
    val hindi : Int,
    val socialScience : Int
)

enum class Grade{
    A,B,C,D,F
}

