import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rushikesh.prakruti.R


val symptomsList = listOf(
    "itching",
    "skin_rash",
    "nodal_skin_eruptions",
    "continuous_sneezing",
    "shivering",
    "chills",
    "joint_pain",
    "stomach_pain",
    "acidity",
    "ulcers_on_tongue",
    "muscle_wasting",
    "vomiting",
    "burning_micturition",
    "spotting_urination",
    "fatigue",
    "weight_gain",
    "anxiety",
    "cold_hands_and_feets",
    "mood_swings",
    "weight_loss",
    "restlessness",
    "lethargy",
    "patches_in_throat",
    "irregular_sugar_level",
    "cough",
    "high_fever",
    "sunken_eyes",
    "breathlessness",
    "sweating",
    "dehydration",
    "indigestion",
    "headache",
    "yellowish_skin",
    "dark_urine",
    "nausea",
    "loss_of_appetite",
    "pain_behind_the_eyes",
    "back_pain",
    "constipation",
    "abdominal_pain",
    "diarrhoea",
    "mild_fever",
    "yellow_urine",
    "yellowing_of_eyes",
    "acute_liver_failure",
    "fluid_overload",
    "swelling_of_stomach",
    "swelled_lymph_nodes",
    "malaise",
    "blurred_and_distorted_vision",
    "phlegm",
    "throat_irritation",
    "redness_of_eyes",
    "sinus_pressure",
    "runny_nose",
    "congestion",
    "chest_pain",
    "weakness_in_limbs",
    "fast_heart_rate",
    "pain_during_bowel_movements",
    "pain_in_anal_region",
    "bloody_stool",
    "irritation_in_anus",
    "neck_pain",
    "dizziness",
    "cramps",
    "bruising",
    "obesity",
    "swollen_legs",
    "swollen_blood_vessels",
    "puffy_face_and_eyes",
    "enlarged_thyroid",
    "brittle_nails",
    "swollen_extremeties",
    "excessive_hunger",
    "extra_marital_contacts",
    "drying_and_tingling_lips",
    "slurred_speech",
    "knee_pain",
    "hip_joint_pain",
    "muscle_weakness",
    "stiff_neck",
    "swelling_joints",
    "movement_stiffness",
    "spinning_movements",
    "loss_of_balance",
    "unsteadiness",
    "weakness_of_one_body_side",
    "loss_of_smell",
    "bladder_discomfort",
    "foul_smell_of_urine",
    "continuous_feel_of_urine",
    "passage_of_gases",
    "internal_itching",
    "toxic_look_(typhos)",
    "depression",
    "irritability",
    "muscle_pain",
    "altered_sensorium",
    "red_spots_over_body",
    "belly_pain",
    "abnormal_menstruation",
    "dischromic_patches",
    "watering_from_eyes",
    "increased_appetite",
    "polyuria",
    "family_history",
    "mucoid_sputum",
    "rusty_sputum",
    "lack_of_concentration",
    "visual_disturbances",
    "receiving_blood_transfusion",
    "receiving_unsterile_injections",
    "coma",
    "stomach_bleeding",
    "distention_of_abdomen",
    "history_of_alcohol_consumption",
    "fluid_overload",
    "blood_in_sputum",
    "prominent_veins_on_calf",
    "palpitations",
    "painful_walking",
    "pus_filled_pimples",
    "blackheads",
    "scurring",
    "skin_peeling",
    "silver_like_dusting",
    "small_dents_in_nails",
    "inflammatory_nails",
    "blister",
    "red_sore_around_nose",
    "yellow_crust_ooze",
    "prognosis"
)

val randomSymptoms = symptomsList.shuffled().take(11)


@SuppressLint("MutableCollectionMutableState")

@Composable
fun QnA(param: String, navController: NavHostController) {


    var currentQuestionIndex by remember { mutableStateOf(0) }

    var answer by remember { mutableStateOf<Boolean?>(null) }


    // Extract the specific data you need
    val paramList = param.split(",")
    val name = paramList[0]
    val days = paramList[1].toInt()
    val primary_symotom = paramList[2]
    val selectedSymptoms by remember { mutableStateOf(mutableListOf<String>()) }
    selectedSymptoms.add(primary_symotom)


    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 50.dp)

    ) {
        Text(
            text = "Hello $name!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Question(randomSymptoms[currentQuestionIndex], answer) { symptom, isYes ->
            if (isYes) {
                selectedSymptoms.add(symptom)
            } else {
                selectedSymptoms.remove(symptom)
            }
            answer = isYes
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
            if (currentQuestionIndex < randomSymptoms.lastIndex) {
                Button(onClick = {
                    currentQuestionIndex++
                    answer = null // Reset answer state
                }) {
                    Text("Next")
                }
            } else {
                Button(onClick = {
                    navController.navigate("result/$name/$days/${selectedSymptoms.joinToString(",")}")
                }) {
                    Text("Submit")
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                Modifier
                    .width(250.dp)
                    .height(170.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bubble),
                    contentDescription = null,
                    Modifier.fillMaxSize()
                )
                Text(
                    text = "Choose your responses with a smile, " +
                            "like chatting with a cute robot " +
                            "buddy!",
                    Modifier
                        .padding(35.dp)
                        .padding(top = 21.dp)
                        .padding(horizontal = 8.dp),
                    fontSize = 10.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
            Image(
                painter = painterResource(id = R.drawable.dignosis), contentDescription = null,
                modifier = Modifier.height(300.dp)
            )


        }
    }

}


@Composable
fun Question(symptom: String, answer: Boolean?, onAnswerSelected: (String, Boolean) -> Unit) {

    Column {

        Text(
            text = "    Do you have any ${symptom.replace("_", " ")}?",
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 20.sp
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
            Text(
                text = "Yes",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = answer == false,
                onClick = {
                    onAnswerSelected(symptom, false)
                }
            )
            Text(
                text = "No",
                fontSize = 18.sp
            )


        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun QnAPreview() {
    // Create a NavHostController instance for preview purposes
    val navController = rememberNavController()

    // Call the QnA composable with sample data
    QnA(param = "John,30", navController = navController)
}
