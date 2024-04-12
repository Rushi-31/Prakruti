package com.rushikesh.prakruti.Composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rushikesh.prakruti.R
import com.rushikesh.prakruti.ui.theme.greenColor

@SuppressLint("SuspiciousIndentation")
@Composable
fun FinalResult(
    navController: NavHostController
) {
    val symptoms = remember {
        // Retrieve selectedSymptoms from arguments
        navController.previousBackStackEntry?.arguments?.getStringArrayList("selectedSymptoms")
            ?: emptyList()
    }
    val name = navController.currentBackStackEntry?.arguments?.getString("name") ?: ""
    val gender = navController.currentBackStackEntry?.arguments?.getString("gender") ?: ""
    val age = navController.currentBackStackEntry?.arguments?.getString("age") ?: ""
    var days = navController.currentBackStackEntry?.arguments?.getString("days") ?: ""
    val daysInt = days.toIntOrNull() ?: 0
    val discreption = remember {
        mutableStateOf("")
    }
    val precautions = remember {
        mutableListOf("")
    }
    val prediction = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    var resultText = ""
    var logo = 0
    var color = Color.Transparent
    if (daysInt < 4) {
        resultText = "This Might not bad but you should take precautions."
        logo = R.drawable.green_light
        color = greenColor
    } else if (daysInt >= 4 && daysInt < 8) {
        resultText = "You might Need a doctor consultation."
        logo = R.drawable.yellow_light
        color = Color.Yellow
    } else {
        resultText = "Call an ambulance !"
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(500.dp),

            horizontalAlignment = Alignment.Start,

            ) {
            Row(
                Modifier
                    .background(color)
                    .fillMaxWidth()
                    .height(70.dp), horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Alarming Symtoms ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            LazyColumn {
                items(symptoms.size) {
                    Text(
                        text = "• ${symptoms[it]}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Result",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,

                )

            Text(text = name, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "You may have ${prediction.value}",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Description: description",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn {
                items(precautions.size) { index ->

                    if (precautions.size > 0 && precautions[index].isNotBlank())
                        Text(
                            text = "• ${precautions[index]}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                }
            }
        }
    }
}


