@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.calculadoradegrasa.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradegrasa.R
import com.example.calculadoradegrasa.calculate.Fat
import com.example.calculadoradegrasa.calculate.FatPercentage
import com.example.calculadoradegrasa.calculate.Muscle
import com.example.calculadoradegrasa.calculate.fatCalculatorForMale
import com.example.calculadoradegrasa.calculate.fatCalculatorForWoman
import com.example.calculadoradegrasa.ui.theme.Blue
import com.example.calculadoradegrasa.ui.theme.CALCULADORADEGRASATheme
import com.example.calculadoradegrasa.ui.theme.NewBlack
import com.example.calculadoradegrasa.ui.theme.Orange
import com.example.calculadoradegrasa.ui.theme.LightBlue
import com.example.calculadoradegrasa.ui.theme.LightRed
import com.example.calculadoradegrasa.ui.theme.Red
import com.example.calculadoradegrasa.ui.theme.White

private var isMale by mutableStateOf(true)

@Composable
fun TopAppBar() {
    var isMan by remember { mutableStateOf(true) }

    TopAppBar(
        title = {
            Text(
                text = "Calculadora de grasa",
                fontSize = 20.sp,
                color = NewBlack
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Orange //Background color
        ),
        actions = {//Switch in the end of top bar
            Switch(
                checked = isMan,
                onCheckedChange = {
                    isMan = it
                },
                thumbContent = //icons
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
                    checkedThumbColor = Blue,//icon background color
                    checkedTrackColor = LightBlue,//background
                    checkedBorderColor = Blue,
                    checkedIconColor = White,
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
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(
                text = "Aclaración esto se calcula con la\n" +
                        "fórmula de Hodgdon–Beckett y\n" +
                        "para los hombre no hace falta poner\n" +
                        "la cadera",
                color = LightBlue,
                textAlign = TextAlign.Left
            )
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
        modifier = Modifier
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
            keyboardOptions = KeyboardOptions(//type of keyboard
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Orange
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(12.5.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            label = { Text("Ingresa tu peso (en kg)") },
            keyboardOptions = KeyboardOptions(//type of keyboard
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Orange
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(12.5.dp))

        OutlinedTextField(
            value = waist,
            onValueChange = { waist = it },
            singleLine = true,
            label = { Text("Ingresa tu cintura (en cm)") },
            keyboardOptions = KeyboardOptions(//type of keyboard
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Orange
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(12.5.dp))

        OutlinedTextField(
            value = neck,
            onValueChange = { neck = it },
            singleLine = true,
            label = { Text("Ingresa tu cuello (en cm)") },
            keyboardOptions = KeyboardOptions(//type of keyboard
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Orange
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(12.5.dp))

        OutlinedTextField(
            value = hip,
            onValueChange = { hip = it },
            singleLine = true,
            label = { Text("Ingresa tu cadera (en cm)") },
            keyboardOptions = KeyboardOptions(//type of keyboard
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Orange,
                disabledBorderColor = Red
            ),
            enabled = !isMale,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.5.dp))

        Box(
           Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(size = 12.dp),
                        color = Orange
                    ),
                onClick = {
                    if (isMale) {
                        fatCalculatorForMale(
                            height = height.toDoubleOrNull() ?: 0.0,
                            waist = waist.toDoubleOrNull() ?: 0.0,
                            neck = neck.toDoubleOrNull() ?: 0.0,
                            weight = weight.toDoubleOrNull() ?: 0.0
                        )
                    } else {
                        fatCalculatorForWoman(
                            height = height.toDoubleOrNull() ?: 0.0,
                            waist = waist.toDoubleOrNull() ?: 0.0,
                            neck = neck.toDoubleOrNull() ?: 0.0,
                            weight = weight.toDoubleOrNull() ?: 0.0,
                            hip = hip.toDoubleOrNull() ?: 0.0
                        )
                    }
                },
                content = {
                    Text(
                        text = "calcular",
                        fontSize = 16.sp,
                        color = NewBlack
                    )
                }
            )
        }

        Spacer(modifier = Modifier.padding(8.5.dp))

        TextsFatCalculator()
    }
}

@Composable
fun TextsFatCalculator() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Porsentaje de grasa : $FatPercentage%\n" +
                    "Cantidad de grasa : $Fat kg\n" +
                    "Cantidad de musculo : $Muscle kg",
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
        containerColor = NewBlack
    ) {innerPadding ->
        Column (
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ){
            TextsFields()
            BottomAppBar()
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