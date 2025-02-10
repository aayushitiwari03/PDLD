package com.poc.pdld.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.poc.pdld.R

@Composable
fun Main(
    modifier: Modifier,
){
    Column (
        modifier = modifier.fillMaxSize(),
    ){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_radio_button_checked_24),
                contentDescription = "Radio Button Checked",
                modifier.size(38.dp),
                tint = Color.Green
            )
            Text(
                text = "Status : Online",
            )
        }

        LazyColumn (){
            items(10){
                Text(
                    text = "Item $it",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ){
            FloatingActionButton(
                onClick = {

                },
                modifier = Modifier
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}