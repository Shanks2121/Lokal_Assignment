package com.example.shanks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shanks.data.model.Job
import com.example.shanks.ui.component.BottomNavItem
import com.example.shanks.ui.component.BottomNavigationBar
import com.example.shanks.ui.component.TopBar
import com.example.shanks.ui.screen.BookmarksScreen
import com.example.shanks.ui.screen.HomeScreen
import com.example.shanks.ui.screen.JobDetailsScreen
import com.example.shanks.ui.theme.AssignmentTheme
import com.example.shanks.ui.theme.LokalPrimaryColor
import com.example.shanks.util.NetworkConnectivityObserver
import com.example.shanks.util.NetworkUtils
import com.example.shanks.vm.JobViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppContainer()
                }
            }
        }
    }
}

@Composable
fun AppContainer(
    viewModel: JobViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val networkObserver = remember { NetworkConnectivityObserver(context) }
    val isOnline = networkObserver.observeAsState(NetworkUtils.isOnline(context))
    Log.d("MainActivity", "${isOnline.value}")

    Scaffold(topBar = {
        TopBar(
            navController = navController
        )
    }, bottomBar = {
        if (!isLoading) BottomNavigationBar(navController = navController)
    }) { innerPadding ->
        NavigationGraph(
            navController = navController,
            viewModel = viewModel,
            isLoading = isLoading,
            isOnline = isOnline,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: JobViewModel,
    isLoading: Boolean,
    isOnline: State<Boolean>,
    modifier: Modifier = Modifier,
) {
    var jobs by remember { mutableStateOf(emptyList<Job>()) }

    if (isOnline.value) {
        jobs = viewModel.jobs.collectAsState(emptyList()).value
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                trackColor = LokalPrimaryColor, color = White
            )
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = if (isOnline.value) BottomNavItem.Home.route else BottomNavItem.Bookmarks.route,
            modifier = modifier
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    jobs = jobs,
                    navController = navController,
                    isOnline = isOnline
                )
            }
            composable(BottomNavItem.Bookmarks.route) {
                BookmarksScreen(
                    viewModel = viewModel, navController = navController, isOnline = isOnline
                )
            }

            composable(
                "details_screen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                val job = jobs.find { it.id == id }
                job?.let {
                    JobDetailsScreen(job = job)
                }
            }
        }
    }
}
