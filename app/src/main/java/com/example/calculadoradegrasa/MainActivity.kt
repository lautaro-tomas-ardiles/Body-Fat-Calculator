@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.calculadoradegrasa

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.calculadoradegrasa.ui.theme.*
import java.lang.Math.log10
import kotlin.math.roundToInt

var GeneralFatPercentage by mutableStateOf(0.0)
var GeneralFat by mutableStateOf(0.0)
var GeneralMuscles by mutableStateOf(0.0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CALCULADORADEGRASATheme(darkTheme = true) {
                MainPage()
            }
        }
    }
}

@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Calculadora de grasa",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp)
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Emerald
        )
    )
}

@Composable
fun TextsFields() {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var neck by remember { mutableStateOf("") }

    Column(
        Modifier
            .padding(vertical = 80.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = height,
            onValueChange = { height = it },
            singleLine = true,
            label = { Text("Ingresa tu altura (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            label = { Text("Ingresa tu peso (en kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        TextField(
            value = waist,
            onValueChange = { waist = it },
            singleLine = true,
            label = { Text("Ingresa tu cintura (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        TextField(
            value = neck,
            onValueChange = { neck = it },
            singleLine = true,
            label = { Text("Ingresa tu cuello (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = {
                fatCalculator(
                    height = height.toDoubleOrNull() ?: 0.0,
                    waist = waist.toDoubleOrNull() ?: 0.0,
                    neck = neck.toDoubleOrNull() ?: 0.0,
                    weight = weight.toDoubleOrNull() ?: 0.0
                )
            }
        ) {
            Text("Calcular")
        }
        TextsFatCalculator()
    }

}

fun fatCalculator(height: Double, waist: Double, neck: Double, weight: Double) {
    var fatPercentage =
        495 / (1.0324 - 0.19077 * kotlin.math.log10((waist - neck)) + 0.15456 * kotlin.math.log10(height)) - 450
    fatPercentage = if (!fatPercentage.isNaN()) (fatPercentage * 100.0).roundToInt() / 100.0 else 0.0

    var fat = (fatPercentage * weight) / 100.0
    fat = if (!fat.isNaN()) (fat * 100.0).roundToInt() / 100.0 else 0.0

    var muscle = weight - fat
    muscle = if (!muscle.isNaN()) (muscle * 100.0).roundToInt() / 100.0 else 0.0

    GeneralFatPercentage = fatPercentage
    GeneralFat = fat
    GeneralMuscles = muscle
}

@Composable
fun TextsFatCalculator() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Porcentaje de grasa: $GeneralFatPercentage",
            color = Color.White,
            fontSize = 19.sp
        )
        Text(
            text = "Cantidad de grasa(en kg): $GeneralFat",
            color = Color.White,
            fontSize = 19.sp
        )
        Text(
            text = "Cantidad de músculo(en kg): $GeneralMuscles",
            color = Color.White,
            fontSize = 19.sp
        )
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "aclaración esto se calcula con la fórmula de Hodgdon–Beckett ",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            TopAppBar()
        },
        containerColor = RaisinBlack
    ) {
        TextsFields()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CALCULADORADEGRASATheme(darkTheme = true) {
        MainPage()
    }
}
