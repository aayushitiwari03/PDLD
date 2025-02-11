package com.poc.pdld.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poc.pdld.data.Results
import com.poc.pdld.data.Subjects
import com.poc.pdld.viewmodel.ResultViewModel


@Composable
fun ResultSheet(
    navController: NavController,
    viewModel: ResultViewModel
) {

    val studentName = remember { mutableStateOf("") }
    val studentClass = remember { mutableStateOf("") }
    val fatherName = remember { mutableStateOf("") }
    val motherName = remember { mutableStateOf("") }
    val rollNo = remember { mutableStateOf("") }
    val maths = remember { mutableStateOf("") }
    val science = remember { mutableStateOf("") }
    val english = remember { mutableStateOf("") }
    val hindi = remember { mutableStateOf("") }
    val socialScience = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = studentName.value, onValueChange = { studentName.value = it }, label = { Text("Student Name") })
        TextField(value = studentClass.value, onValueChange = { studentClass.value = it }, label = { Text("Class") })
        TextField(value = fatherName.value, onValueChange = { fatherName.value = it }, label = { Text("Father's Name") })
        TextField(value = motherName.value, onValueChange = { motherName.value = it }, label = { Text("Mother's Name") })
        TextField(value = rollNo.value, onValueChange = { rollNo.value = it }, label = { Text("Roll Number") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

        Text("Enter Marks", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        TextField(value = maths.value, onValueChange = { maths.value = it }, label = { Text("Maths") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        TextField(value = science.value, onValueChange = { science.value = it }, label = { Text("Science") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        TextField(value = english.value, onValueChange = { english.value = it }, label = { Text("English") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        TextField(value = hindi.value, onValueChange = { hindi.value = it }, label = { Text("Hindi") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        TextField(value = socialScience.value, onValueChange = { socialScience.value = it }, label = { Text("Social Science") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

        Button(
            onClick = {
                val marks = Subjects(
                    maths = maths.value.toIntOrNull() ?: 0,
                    science = science.value.toIntOrNull() ?: 0,
                    english = english.value.toIntOrNull() ?: 0,
                    hindi = hindi.value.toIntOrNull() ?: 0,
                    socialScience = socialScience.value.toIntOrNull() ?: 0
                )
                val totalMarks = marks.maths + marks.science + marks.english + marks.hindi + marks.socialScience
                val result = Results(
                    id = 0,
                    name = studentName.value,
                    clas = studentClass.value,
                    fatherName = fatherName.value,
                    motherName = motherName.value,
                    marks = listOf(marks),
                    totalMarks = totalMarks,
                    rollNo = rollNo.value.toIntOrNull() ?: 0
                )
            viewModel.addResult(result =result)}
        ) {
            Text("Submit")
        }

    }


}