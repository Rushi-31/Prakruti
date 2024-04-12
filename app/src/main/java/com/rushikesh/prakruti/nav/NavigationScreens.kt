package com.rushikesh.prakruti.nav

import QnA
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rushikesh.prakruti.Composable.FinalResult
import com.rushikesh.prakruti.Composable.UserData

@Composable
fun NavigationScreens(navController: NavHostController) {

    NavHost(navController, startDestination = NavItem.UserData.path) {
        composable(NavItem.UserData.path) {
            UserData(navController)
        }
        composable(NavItem.Qna.path) {
            QnA(navController)
        }
        composable(NavItem.FinalResult.path) { FinalResult(navController) }
    }
}