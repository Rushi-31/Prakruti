package com.rushikesh.prakruti.Composable

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rushikesh.prakruti.R
import com.rushikesh.prakruti.api.postData

import com.rushikesh.prakruti.ui.theme.greenColor

@SuppressLint("SuspiciousIndentation")

@Composable
fun FinalResult(
    days: Int,
    symptoms: List<String>,
    navController: NavHostController
) {
    val context = LocalContext.current

    var prediction by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var precautions by remember { mutableStateOf(emptyList<String>()) }
    var symptomList = symptoms.toSet()
    // Call the postData function
    postData(context, symptomList.toMutableList(), days,
        onPredictionReceived = { pred, desc, precaut ->
            prediction = pred
            description = desc
            precautions = precaut
        },
        onFailure = { message ->
            // Handle failure, such as displaying an error message
            Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
        }
    )

    val resultText = when {
        days < 4 -> "This Might not be bad but you should take precautions."
        days in 4..7 -> "You might need a doctor consultation."
        else -> "Call an ambulance!"
    }

    val logo = when {
        days < 4 -> R.drawable.green_light
        days in 4..7 -> R.drawable.yellow_light
        else -> R.drawable.red_light
    }

    val color = when {
        days < 4 -> greenColor
        days in 4..7 -> Color.Yellow
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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
            Text(text = "Alarming Symptoms ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            LazyColumn {
                items(symptomList.size) {
                    Text(
                        text = "• ${symptomList.toMutableList()[it].replace("_", " ")}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
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

            Text(
                text = resultText,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = " Diagnostic: $prediction",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Description", fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Text(
                text = description,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Precautions:",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            LazyColumn {
                items(precautions.size) { index ->
                    if (precautions[index].isNotBlank())
                        Text(
                            text = "• ${precautions[index]}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable

fun FinalResultPreview() {
    // Create a NavHostController instance for preview purposes
    val navController = rememberNavController()

    // Call the FinalResult composable with sample data
    FinalResult(
        days = 5, // Sample days
        symptoms = listOf("Symptom 1", "Symptom 2", "Symptom 3"), // Sample symptoms
        navController = navController
    )
}
