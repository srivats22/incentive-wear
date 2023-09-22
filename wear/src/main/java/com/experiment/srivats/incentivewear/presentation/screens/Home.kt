package com.experiment.srivats.incentivewear.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeView(navController: NavController, userId: String){
    val store = UserStore(LocalContext.current)
    val loginState = store.getAuthState.collectAsState(initial = false)
    val dataStoreUserId = store.getAccessToken.collectAsState(initial = "")
    val listState = rememberScalingLazyListState()
    var isLoading: Boolean by remember{mutableStateOf(false)}
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
            if(isLoading){
                item { CircularProgressIndicator() }
            }
            else{
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
                    Chip(
                        modifier = contentModifier,
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                store.saveAuthState(false)
                                store.saveToken("")
                            }
                            navController.navigate("Landing")
                        },
                        label = {
                            Text(
                                text = "Log Out",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                                contentDescription = "chip icon",
                                modifier = iconModifier,
                            )
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.DarkGray,
                        )
                    )
                }
            }
        }
    }
}