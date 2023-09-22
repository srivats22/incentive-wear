package com.experiment.srivats.incentivewear.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.experiment.srivats.incentivewear.presentation.helper.user.UserStore
import com.experiment.srivats.incentivewear.presentation.screens.CurrTask
import com.experiment.srivats.incentivewear.presentation.screens.HomeView
import com.experiment.srivats.incentivewear.presentation.screens.LoginView
import com.experiment.srivats.incentivewear.presentation.screens.PendingTask
import com.experiment.srivats.incentivewear.presentation.screens.PlannedTask
import com.experiment.srivats.incentivewear.presentation.screens.TaskDetails
import com.experiment.srivats.incentivewear.presentation.theme.IncentiveWearTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            WearApp(applicationContext)
        }
    }
}

@Composable
fun WearApp(context: Context) {
    val store = UserStore(context)
    val swipeDismissableNavController = rememberSwipeDismissableNavController()
    val loginState = store.getAuthState.collectAsState(initial = false)
    val userUid = store.getAccessToken.collectAsState(initial = "")
    IncentiveWearTheme {
        SwipeDismissableNavHost(
            navController = swipeDismissableNavController,
            startDestination = if(!loginState.value) "Landing" else "Home",
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            composable("Landing") {
                LoginView(navController = swipeDismissableNavController)
            }
            composable("Home") {
                HomeView(navController = swipeDismissableNavController, userId = userUid.value)
            }
            composable("currTask"){
                CurrTask(navController = swipeDismissableNavController, userId = userUid.value)
            }
            composable("plannedTask"){
                PlannedTask(navController = swipeDismissableNavController, userId = userUid.value)
            }
            composable("pendingTask"){
                PendingTask(navController = swipeDismissableNavController, userId = userUid.value)
            }
            composable("taskDetails/{taskName}/{taskDetails}"){
                TaskDetails(
                    taskName = it.arguments?.getString("taskName") ?: "",
                    taskDesc = it.arguments?.getString("taskDetails") ?: "",
                )
            }
        }
    }
}
