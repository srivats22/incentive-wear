package com.experiment.srivats.incentivewear.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import com.experiment.srivats.incentivewear.presentation.common.AppChip
import com.experiment.srivats.incentivewear.presentation.helper.user.UserStore

@Composable
fun HomeView(navController: NavController){
    val mContext = LocalContext.current
    val store = UserStore(mContext)
    Log.d("User Id", store.getAccessToken.collectAsState(initial = "").value)
    val listState = rememberScalingLazyListState()
    val contentModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    val iconModifier = Modifier
        .size(24.dp)
        .wrapContentSize(align = Alignment.Center)
    Scaffold(
        timeText = { TimeText(modifier = Modifier.scrollAway(listState)) },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = listState
        ) {
            item {
                Text("Incentive",
                    style = MaterialTheme.typography.display3
                )
            }
            item {
                AppChip(
                    modifier = contentModifier,
                    iconModifier = iconModifier,
                    chipTitle = "Today's Task",
                    chipIcon = Icons.Rounded.Task,
                    navController = navController,
                    destination = "currTask"
                )
            }
            item {
                AppChip(
                    modifier = contentModifier,
                    iconModifier = iconModifier,
                    chipTitle = "Pending Task",
                    chipIcon = Icons.Rounded.PendingActions,
                    navController = navController,
                    destination = "pendingTask"
                )
            }
            item {
                AppChip(
                    modifier = contentModifier,
                    iconModifier = iconModifier,
                    chipTitle = "Planned Task",
                    chipIcon = Icons.Rounded.Task,
                    navController = navController,
                    destination = "plannedTask"
                )
            }
            item {
                AppChip(
                    modifier = contentModifier,
                    iconModifier = iconModifier,
                    chipTitle = "Settings",
                    chipIcon = Icons.Rounded.Settings,
                    navController = navController,
                    destination = "settings"
                )
            }
        }
    }
}