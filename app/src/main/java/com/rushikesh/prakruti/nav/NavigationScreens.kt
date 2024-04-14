package com.rushikesh.prakruti.nav

import QnA
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rushikesh.prakruti.Composable.FinalResult
import com.rushikesh.prakruti.Composable.UserData
@Composable
fun NavigationScreens(navController: NavHostController, modifier: Modifier) {

    NavHost(navController, startDestination = "user_data") {
        composable("user_data") {

            UserData(onNavigateToQna = {
                navController.navigate(
                    "qna/$it",

                    )
            })
        }
        composable(route = "qna/{my_param}",
            arguments = listOf(
                navArgument("my_param") {
                    type = NavType.StringType
                }
            )
        ) {
            val param = it.arguments?.getString("my_param") ?: ""
            QnA(param = param, navController)
        }

        composable(
            route = "result/{name}/{days}/{symptoms}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("days") { type = NavType.StringType },
                navArgument("symptoms") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name") ?: ""
            val days = navBackStackEntry.arguments?.getString("days") ?: ""
            val symptoms =
                navBackStackEntry.arguments?.getString("symptoms")?.split(",") ?: emptyList()
            FinalResult(days = days.toInt(), symptoms = symptoms, navController)
        }

    }
}