package com.experiment.srivats.incentivewear.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun AppChip(modifier: Modifier = Modifier,
            iconModifier: Modifier = Modifier,
            chipTitle: String,
            chipIcon: ImageVector,
            navController: NavController,
            destination: String){
    Chip(
        modifier = modifier,
        onClick = { navController.navigate(destination) },
        label = {
            Text(
                text = chipTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = chipIcon,
                contentDescription = "chip icon",
                modifier = iconModifier,
            )
        },
        colors = ChipDefaults.chipColors(
            backgroundColor = Color.DarkGray,
        )
    )

}