package com.poc.pdld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.poc.pdld.screen.Main
import com.poc.pdld.ui.theme.PDLDTheme
import javax.security.auth.Subject

/* The data can post through apps which will be stored in the database
and posted on the server database when the internet connection will be their*/

class MainActivity : ComponentActivity() {
    private lateinit var networkReceiver: NetworkReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isOnline = remember { mutableStateOf(NetworkReceiver.checkNetworkStatus(this)) }
            networkReceiver = NetworkReceiver.register(this, isOnline)
            PDLDTheme {
                Main(modifier = Modifier, isOnline = isOnline)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkReceiver.unregister(this, networkReceiver)
    }
}
