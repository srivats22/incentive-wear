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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.experiment.srivats.incentivewear.presentation.helper.CurrVMFactory
import com.experiment.srivats.incentivewear.presentation.helper.user.UserStore
import com.experiment.srivats.incentivewear.presentation.helper.vm.CurrVM
import com.experiment.srivats.incentivewear.presentation.screens.CurrTask
import com.experiment.srivats.incentivewear.presentation.screens.HomeView
import com.experiment.srivats.incentivewear.presentation.screens.LoginView
import com.experiment.srivats.incentivewear.presentation.theme.IncentiveWearTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: CurrVM
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
        }
    }
}
