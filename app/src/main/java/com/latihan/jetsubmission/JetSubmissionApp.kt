package com.latihan.jetsubmission

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.latihan.jetsubmission.navigation.NavigationItem
import com.latihan.jetsubmission.navigation.Screen
import com.latihan.jetsubmission.ui.detail.Detail
import com.latihan.jetsubmission.ui.home.HomeScreen
import com.latihan.jetsubmission.ui.keranjang.Keranjang
import com.latihan.jetsubmission.ui.profile.Profile
import com.latihan.jetsubmission.ui.theme.JetSubmissionTheme


@Composable
fun JetSubmissionApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailSkincare.route){
                BottomBar(navController)}},
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = { skincareId ->
                        navController.navigate(Screen.DetailSkincare.createRoute(skincareId))
                    }
                )
            }
            composable(Screen.Keranjang.route){
                val context = LocalContext.current
                Keranjang(
                    onOrderButtonClicked = {message ->
                        shareOrderSkincare(context, message)
                    }
                )
            }
            composable(Screen.Profile.route){
                Profile()
            }
            composable(
                route = Screen.DetailSkincare.route,
                arguments = listOf(navArgument("skincareId"){ type = NavType.LongType}),
            ){
                val id = it.arguments?.getLong("skincareId") ?: -1L
                Detail(
                    skincareId = id,
                    navigateBack = { navController.navigateUp() },
                    navigateToKeranjang = {
                        navController.popBackStack()
                        navController.navigate(Screen.Keranjang.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar(modifier = modifier) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_keranjang),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Keranjang
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                          navController.navigate(item.screen.route){
                              popUpTo(navController.graph.findStartDestination().id){
                                  saveState = true
                              }
                              restoreState = true
                              launchSingleTop = true
                          }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = { Text(item.title)}
            )
        }
    }
}

private fun shareOrderSkincare(context: Context, summary:String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.toko_skincare))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.toko_skincare)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetSubmissionTheme {
        JetSubmissionApp()
    }
}