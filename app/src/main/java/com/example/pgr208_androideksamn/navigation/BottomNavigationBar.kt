package com.example.pgr208_androideksamn.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar {

        // Gir aktiv route som faktisk fungerer i Compose Navigation 2.7.x+
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach { item ->

            NavigationBarItem(

                // Sjekker om denne taben er aktiv
                selected = currentRoute == item.screenRoute::class.qualifiedName,

                onClick = {
                    navController.navigate(item.screenRoute) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },

                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}
