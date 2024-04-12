package com.rushikesh.prakruti.Composable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class MainViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _symptoms = MutableStateFlow(allSymptoms)
    val symptoms = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_symptoms) { text, symptoms ->
            if (text.isBlank()) {
                symptoms
            } else {
                delay(2000L)
                symptoms.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _symptoms.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}


data class Symptom(val name: String) {
    fun doesMatchSearchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

private val allSymptoms = listOf(
    Symptom("itching"),
    Symptom("skin_rash"),
    Symptom("nodal_skin_eruptions"),
    Symptom("continuous_sneezing"),
    Symptom("shivering"),
    Symptom("chills"),
    Symptom("joint_pain"),
    Symptom("stomach_pain"),
    Symptom("acidity"),
    Symptom("ulcers_on_tongue"),
    Symptom("muscle_wasting"),
    Symptom("vomiting"),
    Symptom("burning_micturition"),
    Symptom("spotting_urination"),
    Symptom("fatigue"),
    Symptom("weight_gain"),
    Symptom("anxiety"),
    Symptom("cold_hands_and_feets"),
    Symptom("mood_swings"),
    Symptom("weight_loss"),
    Symptom("restlessness"),
    Symptom("lethargy"),
    Symptom("patches_in_throat"),
    Symptom("irregular_sugar_level"),
    Symptom("cough"),
    Symptom("high_fever"),
    Symptom("sunken_eyes"),
    Symptom("breathlessness"),
    Symptom("sweating"),
    Symptom("dehydration"),
    Symptom("indigestion"),
    Symptom("headache"),
    Symptom("yellowish_skin"),
    Symptom("dark_urine"),
    Symptom("nausea"),
    Symptom("loss_of_appetite"),
    Symptom("pain_behind_the_eyes"),
    Symptom("back_pain"),
    Symptom("constipation"),
    Symptom("abdominal_pain"),
    Symptom("diarrhoea"),
    Symptom("mild_fever"),
    Symptom("yellow_urine"),
    Symptom("yellowing_of_eyes"),
    Symptom("acute_liver_failure"),
    Symptom("fluid_overload"),
    Symptom("swelling_of_stomach"),
    Symptom("swelled_lymph_nodes"),
    Symptom("malaise"),
    Symptom("blurred_and_distorted_vision"),
    Symptom("phlegm"),
    Symptom("throat_irritation"),
    Symptom("redness_of_eyes"),
    Symptom("sinus_pressure"),
    Symptom("runny_nose"),
    Symptom("congestion"),
    Symptom("chest_pain"),
    Symptom("weakness_in_limbs"),
    Symptom("fast_heart_rate"),
    Symptom("pain_during_bowel_movements"),
    Symptom("pain_in_anal_region"),
    Symptom("bloody_stool"),
    Symptom("irritation_in_anus"),
    Symptom("neck_pain"),
    Symptom("dizziness"),
    Symptom("cramps"),
    Symptom("bruising"),
    Symptom("obesity"),
    Symptom("swollen_legs"),
    Symptom("swollen_blood_vessels"),
    Symptom("puffy_face_and_eyes"),
    Symptom("enlarged_thyroid"),
    Symptom("brittle_nails"),
    Symptom("swollen_extremeties"),
    Symptom("excessive_hunger"),
    Symptom("extra_marital_contacts"),
    Symptom("drying_and_tingling_lips"),
    Symptom("slurred_speech"),
    Symptom("knee_pain"),
    Symptom("hip_joint_pain"),
    Symptom("muscle_weakness"),
    Symptom("stiff_neck"),
    Symptom("swelling_joints"),
    Symptom("movement_stiffness"),
    Symptom("spinning_movements"),
    Symptom("loss_of_balance"),
    Symptom("unsteadiness"),
    Symptom("weakness_of_one_body_side"),
    Symptom("loss_of_smell"),
    Symptom("bladder_discomfort"),
    Symptom("foul_smell_of_urine"),
    Symptom("continuous_feel_of_urine"),
    Symptom("passage_of_gases"),
    Symptom("internal_itching"),
    Symptom("toxic_look_(typhos)"),
    Symptom("depression"),
    Symptom("irritability"),
    Symptom("muscle_pain"),
    Symptom("altered_sensorium"),
    Symptom("red_spots_over_body"),
    Symptom("belly_pain"),
    Symptom("abnormal_menstruation"),
    Symptom("dischromic_patches"),
    Symptom("watering_from_eyes"),
    Symptom("increased_appetite"),
    Symptom("polyuria"),
    Symptom("family_history"),
    Symptom("mucoid_sputum"),
    Symptom("rusty_sputum"),
    Symptom("lack_of_concentration"),
    Symptom("visual_disturbances"),
    Symptom("receiving_blood_transfusion"),
    Symptom("receiving_unsterile_injections"),
    Symptom("coma"),
    Symptom("stomach_bleeding"),
    Symptom("distention_of_abdomen"),
    Symptom("history_of_alcohol_consumption"),
    Symptom("blood_in_sputum"),
    Symptom("prominent_veins_on_calf"),
    Symptom("palpitations"),
    Symptom("painful_walking"),
    Symptom("pus_filled_pimples"),
    Symptom("blackheads"),
    Symptom("scurring"),
    Symptom("skin_peeling"),
    Symptom("silver_like_dusting"),
    Symptom("small_dents_in_nails"),
    Symptom("inflammatory_nails"),
    Symptom("blister"),
    Symptom("red_sore_around_nose"),
    Symptom("yellow_crust_ooze"),
    Symptom("prognosis")
)