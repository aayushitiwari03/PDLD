package com.poc.pdld.screen

import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poc.pdld.NavigationItem
import com.poc.pdld.data.Results
import com.poc.pdld.data.Subjects
import com.poc.pdld.getDraft
import com.poc.pdld.saveDraft
import com.poc.pdld.viewmodel.ResultViewModel


@Composable
fun ResultSheet(
    isOnline: MutableState<Boolean>,
    navController: NavController,
    viewModel: ResultViewModel
) {
    val context = navController.context

    val draft = getDraft(context)
    val studentName = remember { mutableStateOf(draft["studentName"] ?: "") }
    val studentClass = remember { mutableStateOf(draft["studentClass"] ?: "") }
    val fatherName = remember { mutableStateOf(draft["fatherName"] ?: "") }
    val motherName = remember { mutableStateOf(draft["motherName"] ?: "") }
    val rollNo = remember { mutableStateOf(draft["rollNo"] ?: "") }
    val maths = remember { mutableStateOf(draft["maths"] ?: "") }
    val science = remember { mutableStateOf(draft["science"] ?: "") }
    val english = remember { mutableStateOf(draft["english"] ?: "") }
    val hindi = remember { mutableStateOf(draft["hindi"] ?: "") }
    val socialScience = remember { mutableStateOf(draft["socialScience"] ?: "") }
    val scroll = rememberScrollState()

    DisposableEffect(Unit) {
        onDispose {
            saveDraft(context, studentName.value, studentClass.value, fatherName.value, motherName.value, rollNo.value, maths.value, science.value, english.value, hindi.value, socialScience.value)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scrollable(
                scroll,
                orientation = Orientation.Vertical,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(value = studentName.value, onValueChange = { studentName.value = it }, label = { Text("Student Name") })
        TextField(value = studentClass.value, onValueChange = { studentClass.value = it }, label = { Text("Class") })
        TextField(value = fatherName.value, onValueChange = { fatherName.value = it }, label = { Text("Father's Name") })
        TextField(value = motherName.value, onValueChange = { motherName.value = it }, label = { Text("Mother's Name") })
        TextField(value = rollNo.value, onValueChange = { rollNo.value = it }, label = { Text("Roll Number") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

        Text("Enter Marks", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))

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
                    rollNo = rollNo.value.toIntOrNull() ?: 0,
                    lastUpdated = System.currentTimeMillis(),
                    isSynced = isOnline.value
                )

                viewModel.addResult(result = result)
                navController.navigate(NavigationItem.Home.route)
                Toast.makeText(navController.context, "Result Submitted", Toast.LENGTH_SHORT).show()

                // Clear draft after submission
                saveDraft(context, "", "", "", "", "", "", "", "", "", "")
            }
        ) {
            Text("Submit Student Result")
        }
    }
}