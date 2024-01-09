@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
package com.example.calculadoradegrasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
                fontSize = 20.sp,
                color = NewBlack
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Orange
        ),
        actions = {
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
                            contentDescription = "woman",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Blue,//la bola
                    checkedTrackColor = LightBlue,//el fondo
                    checkedBorderColor = Blue,//borde
                    checkedIconColor = White,//icono
                    uncheckedThumbColor = Red,
                    uncheckedTrackColor = LightRed,
                    uncheckedBorderColor = Red,
                    uncheckedIconColor = White
                )
            )
        }
    )
    isMale = isMan
}

@Composable
fun BottomAppBar(){
    BottomAppBar (
        containerColor = NewBlack,
        modifier = Modifier
            .height(140.dp)
            .padding(bottom = 26.dp)
    ) {
        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column{
                Text(
                    text = "Aclaración esto se calcula con la",
                    color = LightBlue
                )
                Text(
                    text = "fórmula de Hodgdon–Beckett y",
                    color = LightBlue
                )
                Text(
                    text = "para los hombre no hace falta poner",
                    color = LightBlue
                )
                Text(
                    text = "la cadera",
                    color = LightBlue
                )
            }
        }
    }
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
            .padding(
                vertical = 25.dp,
                horizontal = 55.dp
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            singleLine = true,
            label = { Text("Ingresa tu altura (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(12.5.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            label = { Text("Ingresa tu peso (en kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(12.5.dp))
        OutlinedTextField(
            value = waist,
            onValueChange = { waist = it },
            singleLine = true,
            label = { Text("Ingresa tu cintura (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(12.5.dp))
        OutlinedTextField(
            value = neck,
            onValueChange = { neck = it },
            singleLine = true,
            label = { Text("Ingresa tu cuello (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(12.5.dp))
        OutlinedTextField(
            value = hip,
            onValueChange = { hip = it },
            singleLine = true,
            label = { Text("Ingresa tu cadera (en cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.padding(8.5.dp))
        Button(
            colors =  ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(size = 12.dp),
                    color = Orange
                ),
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
            Text(
                text = "calcular",
                fontSize = 16.sp,
                color = NewBlack
            )
        }
        Spacer(modifier = Modifier.padding(12.5.dp))
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
            text = "Porsentaje de grasa : $GeneralFatPercentage%\n" +
                    "Cantidad de grasa : $GeneralFat kg\n" +
                    "Cantidad de musculo : $GeneralMuscles kg",
            fontSize = 20.sp,
            color = Orange
        )
    }
}

@Composable
fun MainPage() {
    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomAppBar()
        },
        containerColor = NewBlack
    ) {innerPadding ->
        Column (
            Modifier.padding(innerPadding)
        ){
            TextsFields()
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun Preview() {
    CALCULADORADEGRASATheme(darkTheme = true) {
        MainPage()
    }
}