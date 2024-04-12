import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rushikesh.prakruti.nav.NavItem

val symptomsList = listOf(
    "itching", "skin rash", "nodal skin eruptions", "continuous sneezing", "shivering",
    "chills", "joint pain", "stomach pain", "acidity", "ulcers on tongue"
)

@SuppressLint("MutableCollectionMutableState")

@Composable
fun QnA(navController: NavHostController) {


    var currentQuestionIndex by remember { mutableStateOf(0) }
    val selectedSymptoms by remember { mutableStateOf(mutableListOf<String>()) }
    var answer by remember { mutableStateOf<Boolean?>(null) }



    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Question(symptomsList[currentQuestionIndex], answer) { symptom, isYes ->
            if (isYes) {
                selectedSymptoms.add(symptom)
            } else {
                selectedSymptoms.remove(symptom)
            }
            answer = isYes // Update answer state
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentQuestionIndex > 0) {
                Button(onClick = {
                    currentQuestionIndex--
                    answer = null // Reset answer state
                }) {
                    Text("Previous")
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (currentQuestionIndex < symptomsList.lastIndex) {
                Button(onClick = {
                    currentQuestionIndex++
                    answer = null // Reset answer state
                }) {
                    Text("Next")
                }
            } else {
                Button(onClick = {

                    navController.navigate(
                        NavItem.FinalResult.path
                    ) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = false }
                        }
                    }
                }
                ) {
                    Text("Submit")
                }
            }
        }
    }

}


@Composable
fun Question(symptom: String, answer: Boolean?, onAnswerSelected: (String, Boolean) -> Unit) {

    Column {
        val formatedSymptom = symptom.replace("_", " ")
        Text(
            text = "Do you have any $formatedSymptom?",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = answer == true,
                onClick = {
                    onAnswerSelected(symptom, true)
                }
            )
            Text(text = "Yes")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = answer == false,
                onClick = {
                    onAnswerSelected(symptom, false)
                }
            )
            Text(text = "No")
        }

    }
}

