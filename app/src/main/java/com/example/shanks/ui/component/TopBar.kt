package com.example.shanks.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shanks.R

import com.example.shanks.ui.component.BottomNavItem
import com.example.shanks.ui.component.currentRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lokal_logo),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp)
                )
                Text(
                    text = when (currentRoute) {
                        BottomNavItem.Home.route -> "Home"
                        BottomNavItem.Bookmarks.route -> "Bookmarks"
                        "details_screen/{id}" -> "Job Details"
                        else -> "App"
                    },
                    color = Color.White,
                    modifier = Modifier.padding(end = 30.dp)
                )
            }
        }
    )
}