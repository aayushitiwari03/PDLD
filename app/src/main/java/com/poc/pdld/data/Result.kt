package com.poc.pdld.data

import javax.security.auth.Subject

data class Result(
    val name:String,
    val clas : String,
    val fatherName : String,
    val motherName : String,
    val marks : List<Subject>,
    val totalMarks : Int,
    val percentage : Int,
    val grade : List<Grade>
)

data class Subject(
    val maths: Int,
    val science: Int,
    val english:Int,
    val hindi : Int,
    val socialScience : Int
)

enum class Grade{
    A,B,C,D,F
}