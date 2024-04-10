package com.rushikesh.prakruti

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rushikesh.prakruti.api.DataModel
import com.rushikesh.prakruti.api.DiseaseResponse
import com.rushikesh.prakruti.api.RetrofitInstance
import com.rushikesh.prakruti.ui.theme.PrakrutiTheme
import com.rushikesh.prakruti.ui.theme.greenColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrakrutiTheme {
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(),
                ) {
                    postDataui()
                }
            }
        }
    }
}


@Composable
fun postDataui() {
    val context = LocalContext.current

    val symptoms = remember {
        mutableStateOf(TextFieldValue())
    }

    val days = remember {
        mutableStateOf(TextFieldValue())
    }
    val predicted_disease = remember {
        mutableStateOf("")
    }
    val precautions = remember {
        mutableListOf<String>("")
    }
    val discreption = remember {
        mutableStateOf(
            ""
        )
    }

    // on below line we are creating a column.
    Column(
        // on below line we are adding a modifier to it
        // and setting max size, max height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        // on below line we are adding vertical
        // arrangement and horizontal alignment.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            // on below line we are specifying text as
            // Session Management in Android.
            text = "Prakruti",
            // on below line we are specifying text color.
            color = greenColor,
            fontSize = 20.sp,
            // on below line we are specifying font family
            fontFamily = FontFamily.Default,
            // on below line we are adding font weight
            // and alignment for our text
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
        //on below line we are adding spacer
        Spacer(modifier = Modifier.height(5.dp))
        // on below line we are creating a text field for our email.
        TextField(
            // on below line we are specifying value for our email text field.
            value = symptoms.value,
            // on below line we are adding on value change for text field.
            onValueChange = {
                symptoms.value = it
            },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter Your Symptoms Seperated by ',' ") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(5.dp))
        // on below line we are creating a text field for our email.
        TextField(
            // on below line we are specifying value for our email text field.
            value = days.value,
            // on below line we are adding on value change for text field.
            onValueChange = { days.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter Number of Days") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we ar adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(10.dp))
        // on below line we are creating a button
        Button(
            onClick = {
                // on below line we are calling make payment method to update data.
                postData(
                    context = context,
                    symptoms = symptoms,
                    days = days,
                    predicted_disease = predicted_disease,
                    discreption = discreption,
                    precautions = precautions


                )
            },
            // on below line we are adding modifier to our button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "Post Data", modifier = Modifier.padding(8.dp))
        }
        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are creating a text for displaying a response.
        Text(
            text = predicted_disease.value,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun postData(

    context: Context,
    symptoms: MutableState<TextFieldValue>,
    days: MutableState<TextFieldValue>,
    predicted_disease: MutableState<String>,
    discreption: MutableState<String>,
    precautions: MutableList<String>

) {
    var symptomsList = symptoms.value.text.split(",")
    val intDays = days.value.text.toIntOrNull() ?: 0

    val dataModel = intDays.let { DataModel(it, symptomsList) }

    RetrofitInstance.apiInterface.postData(dataModel)?.enqueue(object : Callback<DiseaseResponse?> {
        override fun onResponse(
            call: Call<DiseaseResponse?>,
            response: Response<DiseaseResponse?>
        ) {
            Toast.makeText(context, "Data posted to API", Toast.LENGTH_SHORT).show()
            val diseaseResonse: DiseaseResponse? = response.body()

            if (diseaseResonse != null) {
                predicted_disease.value = diseaseResonse.predicted_disease
                discreption.value = diseaseResonse.description
                precautions.addAll(diseaseResonse.precautions)
            }

        }

        override fun onFailure(call: Call<DiseaseResponse?>, t: Throwable) {
            predicted_disease.value = "Error found is : " + t.message
            discreption.value = "Error found is : " + t.message

        }
    })
}



