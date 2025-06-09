package com.example.calculadoradegrasa.calculate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var FatPercentage by mutableStateOf(0.0)
var Fat by mutableStateOf(0.0)
var Muscle by mutableStateOf(0.0)
var isMale by mutableStateOf(true)