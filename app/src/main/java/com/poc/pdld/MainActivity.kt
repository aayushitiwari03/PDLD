package com.poc.pdld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.poc.pdld.database.StudentDatabase
import com.poc.pdld.repository.ResultRepository
import com.poc.pdld.screen.Main
import com.poc.pdld.screen.ResultSheet
import com.poc.pdld.ui.theme.PDLDTheme
import com.poc.pdld.viewmodel.ResultViewModel
import com.poc.pdld.viewmodel.ResultViewModelFactory

/*

The data can post through apps which will be stored in the database
and posted on the server database when the internet connection will be their

1. Offline Data Submission - Room (Data is submitting and deleting for now offline mode)
2. Automatic Sync When Internet is Available - Firestore - Almost done just add your data locally when internet
is available it updates on the server database + fetch the data from remote server for other user
3. Data Caching When User is Inactive from screen - AutoSaving
4. Offline Search Functionality - Search Through Database (Query from Room + Firestore)

*/

//current issue 2nd point - issue with showing the updated last sync although it's working fine from the backend.

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ResultViewModel
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = StudentDatabase.getInstance(this)
        val repository = ResultRepository(database.resultDao())

        setContent {
            val isOnline = remember { mutableStateOf(NetworkReceiver.checkNetworkStatus(this)) }
            networkReceiver = NetworkReceiver.register(this, isOnline)

            viewModel = ViewModelProvider(this, ResultViewModelFactory(repository))[ResultViewModel::class.java]


            LaunchedEffect(isOnline.value) {
                if (isOnline.value) {
                    viewModel.syncWithFirestore()
                }
            }

            PDLDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResultNavHost(modifier = Modifier, isOnline = isOnline, navController = rememberNavController(), viewModel = viewModel)
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
    viewModel: ResultViewModel,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            Main(navController = navController, isOnline = isOnline, viewModel = viewModel)
        }
        composable(NavigationItem.ResultSheet.route) {
            ResultSheet(navController = navController, viewModel = viewModel, isOnline = isOnline)
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
