package com.rushikesh.prakruti.Composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rushikesh.prakruti.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserData(onNavigateToQna: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(horizontal = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.doctor),
            contentDescription = "doctor logo",
            Modifier
                .height(300.dp)
                .width(250.dp)
                .padding(40.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Let's get started", fontSize = 20.sp)
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var isExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                TextField(
                    value = TextFieldValue(gender, TextRange(gender.length)),
                    onValueChange = { gender = it.text },
                    readOnly = true,
                    placeholder = { Text(text = "Gender") },
                    modifier = Modifier
                        .menuAnchor()
                        .width(150.dp)
                        .padding(vertical = 8.dp)
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Male") },
                        onClick = {
                            gender = "Male"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Female") },
                        onClick = {
                            gender = "Female"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Prefer not to say") },
                        onClick = {
                            gender = "Other"
                            isExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }

        val viewModel = viewModel<MainViewModel>()
        val searchText by viewModel.searchText.collectAsState()
        val symptoms by viewModel.symptoms.collectAsState()
        val selectedSymptom = remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        TextField(
            value = searchText,
            onValueChange = { searchText ->
                selectedSymptom.value = searchText
//                if (selectedSymptom)
                viewModel.onSearchTextChange(searchText)
                expanded = !expanded
            },
            placeholder = { Text(text = "Your primary symptom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "Clear") },
                onClick = {
                    expanded = false
                    selectedSymptom.value = ""
                    viewModel.onSearchTextChange("")
                }
            )
            symptoms.forEach { symptom ->
                DropdownMenuItem(
                    text = { Text(text = symptom.name.replace("_", " ")) },
                    onClick = {
                        expanded = false
                        selectedSymptom.value = symptom.name
                        viewModel.onSearchTextChange(symptom.name)
                    }
                )
            }
        }



        TextField(
            value = days,
            onValueChange = { days = it },
            label = { Text(text = "From how many days") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                if (name.isNotBlank() && gender.isNotBlank() && age.isNotBlank() && days.isNotBlank()) {
                    onNavigateToQna("$name,$days,$searchText")
                } else {
                    val show =
                        Toast.makeText(context, "all should be filled", Toast.LENGTH_SHORT).show()
                }

            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Submit")
        }


    }
}

