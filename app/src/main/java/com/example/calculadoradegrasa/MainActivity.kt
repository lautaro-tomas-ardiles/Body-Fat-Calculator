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
import androidx.compose.ui.res.painterResource
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
var isMale by mutableStateOf(false)

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
    var isMan by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Calculadora de grasa",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Emerald
        ),
        actions = {
            Text(
                text = "Genero :",
                color = Color(0xFF130374),
                fontSize = 20.sp
            )
            Switch(
                checked = isMan,
                onCheckedChange = {
                    isMan = it
                },
                thumbContent =
                if (isMan) {
                    {
                        Icon(
                            painter = painterResource(R.drawable.man),
                            contentDescription = "man",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    {
                        Icon(
                            painter = painterResource(R.drawable.woman),
                            contentDescription = "man",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF150097),//la bola
                    checkedTrackColor = Color(0xFF6744F2),//el fondo
                    checkedBorderColor = Color(0xFF6744F2),//borde
                    checkedIconColor = Color.White,//icono
                    uncheckedThumbColor = Color(0xFF6A016C),
                    uncheckedTrackColor = Color(0xFFF23CF6),
                    uncheckedBorderColor = Color(0xFF8A00CC),
                    uncheckedIconColor = Color.White
                )
            )
        }
    )
    isMale = isMan
}

@Composable
fun BottomAppBar(){
    BottomAppBar(
        actions = {
            Text(
                text = "aclaración esto se calcula con la fórmula de Hodgdon–Beckett(en los hombre no hace falta poner la cadera) ",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        },
        containerColor = RaisinBlack
    )
}

@Composable
fun TextsFields() {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var neck by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            label = { Text("Ingresa tu peso (en kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = waist,
            onValueChange = { waist = it },
            singleLine = true,
            label = { Text("Ingresa tu cintura (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = neck,
            onValueChange = { neck = it },
            singleLine = true,
            label = { Text("Ingresa tu cuello (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(15.dp))
        TextField(
            value = hip,
            onValueChange = { hip = it },
            singleLine = true,
            label = { Text("Ingresa tu cadera (en cm)") },
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
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    hip = hip.toDoubleOrNull() ?: 0.0,
                    isMan = isMale
                )
            }
        ) {
            Text("Calcular")
        }
        TextsFatCalculator()
    }

}

fun fatCalculator(height: Double, waist: Double, neck: Double, weight: Double, hip: Double, isMan: Boolean) {
    if (isMan) {
        var fatPercentage = 495 / (1.0324 - 0.19077 * log10((waist - neck)) + 0.15456 * log10(height)) - 450

        fatPercentage = if (!fatPercentage.isNaN()) {
            (fatPercentage * 100.0).roundToInt() / 100.0
        } else 0.0

        var fat = (fatPercentage * weight) / 100.0

        fat = if (!fat.isNaN()){
            (fat * 100.0).roundToInt() / 100.0
        } else 0.0

        var muscle = weight - fat

        muscle = if (!muscle.isNaN()) {
            (muscle * 100.0).roundToInt() / 100.0
        } else 0.0

        GeneralFatPercentage = fatPercentage
        GeneralFat = fat
        GeneralMuscles = muscle
    } else {
        var fatPercentage = 495 / (1.29579-0.35004 * log10((waist + hip - neck)) + 0.22100 * log10(height)) - 450

        fatPercentage = if (!fatPercentage.isNaN()) {
            (fatPercentage * 100.0).roundToInt() / 100.0
        } else 0.0

        var fat = (fatPercentage * weight) / 100.0

        fat = if (!fat.isNaN()){
            (fat * 100.0).roundToInt() / 100.0
        } else 0.0

        var muscle = weight - fat

        muscle = if (!muscle.isNaN()) {
            (muscle * 100.0).roundToInt() / 100.0
        } else 0.0

        GeneralFatPercentage = fatPercentage
        GeneralFat = fat
        GeneralMuscles = muscle
    }
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
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomAppBar()
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
