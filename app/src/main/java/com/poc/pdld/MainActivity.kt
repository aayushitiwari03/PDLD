package com.poc.pdld

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poc.pdld.screen.Main
import com.poc.pdld.screen.ResultSheet
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResultNavHost(isOnline= isOnline, modifier = Modifier, navController = rememberNavController())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkReceiver.unregister(this, networkReceiver)
    }
}


@Composable
fun ResultNavHost(
    isOnline: MutableState<Boolean>,
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            Main(modifier = modifier, navController = navController, isOnline = isOnline)
        }
        composable(NavigationItem.ResultSheet.route) {
            ResultSheet(navController = navController)
        }
    }
}


enum class Screen{
    HOME,
    RESULT_SHEET
}

sealed class NavigationItem(val route: String) {

    data object Home : NavigationItem(Screen.HOME.name)
    data object ResultSheet : NavigationItem(Screen.RESULT_SHEET.name)

}
