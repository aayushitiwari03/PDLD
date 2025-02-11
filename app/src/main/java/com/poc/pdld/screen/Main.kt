package com.poc.pdld.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.poc.pdld.NavigationItem
import com.poc.pdld.R
import com.poc.pdld.data.Results
import com.poc.pdld.viewmodel.ResultViewModel

@Composable
fun Main(
    navController: NavController,
    isOnline: MutableState<Boolean>,
    viewModel: ResultViewModel
) {
    val lastSynced by viewModel.getLastSyncedTime().observeAsState()

    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        Row (
            modifier = Modifier
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp,
                )
                .fillMaxWidth()
                .wrapContentSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(R.drawable.baseline_radio_button_checked_24),
                contentDescription = if (isOnline.value) "Online" else "Offline",
                modifier = Modifier.size(24.dp),
                tint = if (isOnline.value) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = if (isOnline.value) "Status : Online" else "Status: Offline",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }

        Text(
            text = "Last Synced: ${getTimeAgo(lastSynced ?: 0)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )

        ShowResults(navController = navController, viewModel = viewModel)

    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() +16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ){
        FloatingActionButton(
            onClick = {
                navController.navigate(NavigationItem.ResultSheet.route)
            },

            modifier = Modifier
                .size(56.dp).align(Alignment.End),
            containerColor = Color(0xFF7D5260)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ShowResults(navController: NavController, viewModel: ResultViewModel) {

        val results by viewModel.getAllResults().observeAsState(emptyList())

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                items(results) { result ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(
                                modifier = Modifier.padding(16.dp)) {
                                Text("Name : ${result.name}", fontWeight = FontWeight.Bold)
                                Text("Class : ${result.clas}")
                                Text("Roll No: ${result.rollNo}")
                                Text("Total Marks: ${result.totalMarks}", fontWeight = FontWeight.Bold)
                            }

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.size(28.dp).align(Alignment.CenterVertically)
                                    .clickable {
                                        viewModel.deleteResult(result.id)
                                        Toast.makeText(navController.context, "Result Delete", Toast.LENGTH_SHORT).show()
                                    },
                                tint = Color.Black
                            )

                        }
                }
        }

    }
}

fun getTimeAgo(timestamp: Long): String {

    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val minutes = diff / (1000 * 60)
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> "Just now"
        minutes == 1L -> "1 minute ago"
        minutes < 60 -> "$minutes minutes ago"
        hours == 1L -> "1 hour ago"
        hours < 24 -> "$hours hours ago"
        days == 1L -> "1 day ago"
        else -> "$days days ago"
    }
}